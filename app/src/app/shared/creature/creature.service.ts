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

  get(id: string, compute: boolean = true): Observable<Creature> {
    return this.restangular.one('creatures', id).get({compute});
  }

  count(compute: boolean = true): Observable<Page> {
    return this.baseRest.customGET('', {size: 1, compute});
  }

  list(size: number, page: number, compute: boolean = true, sex?: string): Observable<Creature[]> {
    const param: any = {size, page, sort: 'id,asc', compute};
    if (sex != null) {
      param.sex = sex;
    }
    return this.baseRest.getList(param);
  }

  update(creature: any): Observable<any> {
    return creature.put();
  }

  delete(creature: any): Observable<any> {
    return creature.remove();
  }

  redeem(id: string): Observable<Creature> {
    return this.baseRest.customPOST({}, id + '/action/redeem');
  }
}
