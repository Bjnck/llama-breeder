import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Creature, CreatureInfo} from './creature.interface';
import {CreatureSearch} from './creature-search.interface';
import {RestService} from '../rest/rest.service';
import {map, switchMap} from 'rxjs/operators';
import {CreatureCacheService} from './creature-cache.service';

@Injectable()
export class CreatureService {
  constructor(private restService: RestService) {
  }

  get(id: string, compute: boolean = true): Observable<Creature> {
    return this.restService.rest().pipe(switchMap(rest =>
      rest.one('creatures', id).get({compute}) as Observable<Creature>));
  }

  list(size: number, page: number, compute: boolean = true, search?: CreatureSearch): Observable<Creature[]> {
    const param: any = {size, page, sort: 'id,asc', compute};
    if (search) {
      if (search.generation) {
        param.generation = search.generation;
      }
      if (search.sex) {
        param.sex = search.sex;
      }
      if (search.inPen) {
        param.inpen = search.inPen;
      }
      if (search.maxMaturity) {
        param.maxmaturity = search.maxMaturity;
      }
      if (search.pregnant) {
        param.pregnant = search.pregnant;
      }
      if (search.minPregnancyCount) {
        param.minpregnancycount = search.minPregnancyCount;
      }
      if (search.ids) {
        param.ids = search.ids;
      }
    }
    return this.restService.rest().pipe(switchMap(rest =>
      rest.all('creatures').getList(param) as Observable<Creature[]>));
  }

  update(creature: any): Observable<any> {
    return this.restService.rest(creature).pipe(switchMap(rest => rest.put()));
  }

  delete(creature: any): Observable<any> {
    return this.restService.rest(creature).pipe(switchMap(rest =>
      rest.remove().pipe(map(value => {
        CreatureCacheService.decrementTotalElements();
        return value;
      }))));
  }

  redeem(id: string): Observable<Creature> {
    return this.restService.rest().pipe(switchMap(rest =>
      rest.all('creatures').customPOST({}, id + '/action/redeem').pipe(map(value => {
        CreatureCacheService.incrementTotalElements();
        return value;
      })) as Observable<Creature>));
  }

  getPrices(): Observable<CreatureInfo[]> {
    return this.restService.rest().pipe(switchMap(rest =>
      rest.all('creatures/info').getList() as Observable<CreatureInfo[]>));
  }
}
