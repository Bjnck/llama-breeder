import {Injectable} from '@angular/core';
import {forkJoin, Observable} from 'rxjs';
import {Pen, PenContent, PenInfo} from './pen.interface';
import {Item} from '../shared/item/item.interface';
import {PenActivation} from './pen-activation.interface';
import {PenWithContent} from './pen-with-content.interface';
import {flatMap, map, switchMap} from 'rxjs/operators';
import {Creature} from '../shared/creature/creature.interface';
import {ItemService} from '../shared/item/item.service';
import {CreatureService} from '../shared/creature/creature.service';
import {RestService} from '../shared/rest/rest.service';

@Injectable()
export class PenService {

  constructor(private restService: RestService,
              private itemService: ItemService,
              private creatureService: CreatureService) {
  }

  private static getPenWithContent(pen: Pen, items: Item[], creatures: Creature[]): PenWithContent {
    return {pen, creatures, items};
  }

  create(): Observable<PenWithContent> {
    return this.restService.restFull().all('pens').post()
      .pipe(flatMap((response: any) =>
        this.restService.rest().oneUrl('pens', response.headers.headers.get('location')).get()
          .pipe(map((pen: Pen) => PenService.getPenWithContent(pen, [], [])))));
  }

  update(pen: any): Observable<any> {
    return this.restService.rest(pen).put();
  }

  get(id: string): Observable<Pen> {
    return this.restService.rest().one('pens', id).get();
  }

  getWithContent(id: string): Observable<PenWithContent> {
    return this.get(id)
      .pipe(
        switchMap((pen: Pen) => this.withContent(pen)));
  }

  list(compute: boolean = true): Observable<Pen[]> {
    return this.restService.rest().all('pens').getList({compute});
  }

  listWithContent(): Observable<PenWithContent[]> {
    return this.list()
      .pipe(
        switchMap((pens: Pen[]) => {
          return forkJoin(
            pens.map(async (pen: Pen) => this.withContent(pen))
          );
        })
      );
  }

  activateItem(pen: Pen, item: Item): Observable<PenActivation> {
    return this.restService.rest().all('pens').customPOST({}, pen.id + '/action/activate-item/' + item.id);
  }


  private async withContent(pen: Pen): Promise<PenWithContent> {
    const promises: Promise<any>[] = [];

    const items: Item[] = [];
    pen.items.map((content: PenContent) => {
      promises.push(
        this.itemService.get(content.id, false)
          .pipe(
            map((item: Item) => items.push(item)))
          .toPromise());
    });

    let creatures: Creature[] = [];
    if (pen.creatures.length > 0) {
      promises.push(this.creatureService.list(20, 0, false,
        {ids: pen.creatures.map(content => content.id)})
        .pipe(map((c: Creature[]) => creatures = c))
        .toPromise());
    }

    await Promise.all(promises).catch(error => {
      throw error;
    });

    return new Observable<PenWithContent>(observer => {
      observer.next(PenService.getPenWithContent(pen, items, creatures));
      observer.complete();
    }).toPromise();
  }

  getPrices(): Observable<PenInfo[]> {
    return this.restService.rest().all('pens/info').getList();
  }
}
