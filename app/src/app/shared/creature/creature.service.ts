import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Restangular} from 'ngx-restangular';
import {Creature} from './creature.interface';
import {Page} from '../page/page.interface';

@Injectable()
export class CreatureService {

  baseRest = this.restangular.all('creatures');

  constructor(private restangular: Restangular) {
  }

  get(id: string): Observable<Creature> {
    return this.restangular.one('creatures', id).get();
  }

  count(): Observable<Page> {
    return this.baseRest.customGET('', {size: 1});
  }

  list(size: number, page: number, sex?: string): Observable<Creature[]> {
    let param;
    if (sex) {
      param = {size, page, sex, sort: 'id,asc'};
    } else {
      param = {size, page, sort: 'id,asc'};
    }
    return this.baseRest.getList(param);
  }

  update(creature: any): Observable<any> {
    return creature.put();
  }

  delete(creature: any): Observable<any> {
    return creature.remove();
  }
}
