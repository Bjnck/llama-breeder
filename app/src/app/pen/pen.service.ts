import {Injectable} from '@angular/core';
import {Restangular} from 'ngx-restangular';
import {forkJoin, Observable} from 'rxjs';
import {Pen, PenContent} from './pen.interface';
import {Item} from '../shared/item/item.interface';
import {PenActivation} from './pen-activation.interface';
import {PenWithContent} from './pen-with-content.interface';
import {map, switchMap} from 'rxjs/operators';
import {Creature} from '../shared/creature/creature.interface';
import {ItemService} from '../shared/item/item.service';
import {CreatureService} from '../shared/creature/creature.service';

@Injectable()
export class PenService {

  baseRest = this.restangular.all('pens');

  constructor(private restangular: Restangular,
              private itemService: ItemService,
              private creatureService: CreatureService) {
  }

  private static getPenWithContent(pen: Pen, items: Item[], creatures: Creature[]): PenWithContent {
    return {pen, creatures, items};
  }

  update(pen: any): Observable<any> {
    return pen.put();
  }

  get(id: string): Observable<Pen> {
    return this.restangular.one('pens', id).get();
  }

  getWithContent(id: string): Observable<PenWithContent> {
    return this.get(id)
      .pipe(
        switchMap((pen: Pen) => this.withContent(pen)));
  }

  list(compute: boolean = true): Observable<Pen[]> {
    return this.baseRest.getList({compute});
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
    return this.baseRest.customPOST({}, pen.id + '/action/activate-item/' + item.id);
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

    const creatures: Creature[] = [];
    pen.creatures.map((content: PenContent) => {
      promises.push(
        this.creatureService.get(content.id, false)
          .pipe(
            map((creature: Creature) => creatures.push(creature)))
          .toPromise());
    });

    await Promise.all(promises).catch(error => {
      throw error;
    });

    return new Observable<PenWithContent>(observer => {
      observer.next(PenService.getPenWithContent(pen, items, creatures));
      observer.complete();
    }).toPromise();
  }
}
