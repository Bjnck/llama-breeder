import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Restangular} from 'ngx-restangular';
import {Creature} from './creature.interface';
import {Page} from '../page/page.interface';
import {CreatureSearch} from './creature-search.interface';

@Injectable()
export class CreatureService {
  private static totalElements;
  private static filterElements;

  baseRest = this.restangular.all('creatures');

  constructor(private restangular: Restangular) {
  }

  static getTotalElements(): number {
    return this.totalElements;
  }

  static setTotalElements(elements: number) {
    if (!this.totalElements) {
      this.totalElements = elements;
    }
  }

  static incrementTotalElements() {
    this.totalElements++;
  }

  static decrementTotalElements() {
    this.totalElements--;
  }

  static getFilterElements(): number {
    return CreatureService.filterElements;
  }

  static setFilterElements(elements: number) {
    CreatureService.filterElements = elements;
  }

  get(id: string, compute: boolean = true): Observable<Creature> {
    return this.restangular.one('creatures', id).get({compute});
  }

  count(compute: boolean = true): Observable<Page> {
    return this.baseRest.customGET('', {size: 1, compute});
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
      if (search.minLove) {
        param.minlove = search.minLove;
      }
      if (search.maxMaturity) {
        param.maxmaturity = search.maxMaturity;
      }
      if (search.maxPregnancyEndTime) {
        // 2021-10-16T15:20:39+02:00
        console.log(search.maxPregnancyEndTime.toISOString());
        param.maxpregnancyendtime = search.maxPregnancyEndTime.toISOString();
      }
      if (search.pregnant) {
        param.pregnant = search.pregnant;
        param.minpregnancyendtime = search.minPregnancyEndTime.toISOString();
      }
      if (search.minPregnancyCount) {
        param.minpregnancycount = search.minPregnancyCount;
      }
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
