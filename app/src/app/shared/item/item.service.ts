import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Page} from '../page/page.interface';
import {Item} from './item.interface';
import {RestService} from '../rest/rest.service';
import {ItemCacheService} from './item-cache.service';
import {map} from 'rxjs/operators';
import {CreatureSearch} from "../creature/creature-search.interface";
import {Creature} from "../creature/creature.interface";
import {ItemSearch} from "./item-search.interface";

@Injectable()
export class ItemService {

  constructor(private restService: RestService) {
  }

  get(id: string, compute: boolean = true): Observable<Item> {
    return this.restService.rest().one('items', id).get({compute});
  }

  add(code: string, quality: number): Observable<any> {
    return this.restService.rest().all('items').post({code, quality})
      .pipe(map(value => {
        ItemCacheService.incrementTotalElements();
        return value;
      }));
  }

  count(code?: string, quality?: number, compute: boolean = true): Observable<Page> {
    const params: any = {size: 1, compute};
    if (code) {
      params.code = code;
    }
    if (quality) {
      params.quality = quality;
    }

    return this.restService.rest().all('items').customGET('', params);
  }

  list(size: number, page: number, compute: boolean = true, search?: ItemSearch): Observable<Item[]> {
    const param: any = {size, page, sort: 'id,asc', compute};
    if (search) {
      if (search.code) {
        param.code = search.code;
      }
      if (search.inPen) {
        param.inpen = search.inPen;
      }
      if (search.maxLife) {
        param.maxLife = search.maxLife;
      }
    }
    return this.restService.rest().all('items').getList(param);
  }

  delete(item: any): Observable<any> {
    return this.restService.rest(item).remove();
  }
}
