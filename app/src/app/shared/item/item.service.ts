import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Page} from '../page/page.interface';
import {Item} from './item.interface';
import {RestService} from '../rest/rest.service';
import {ItemCacheService} from './item-cache.service';
import {map, switchMap} from 'rxjs/operators';
import {ItemSearch} from './item-search.interface';

@Injectable()
export class ItemService {

  constructor(private restService: RestService) {
  }

  get(id: string, compute: boolean = true): Observable<Item> {
    return this.restService.rest().pipe(switchMap(rest =>
      rest.one('items', id).get({compute}) as Observable<Item>));
  }

  add(code: string, quality: number): Observable<any> {
    return this.restService.rest().pipe(switchMap(rest =>
      rest.all('items').post({code, quality}).pipe(map(value => {
        ItemCacheService.incrementTotalElements();
        return value;
      }))));
  }

  count(code?: string, quality?: number, compute: boolean = true): Observable<Page> {
    const params: any = {size: 1, compute};
    if (code) {
      params.code = code;
    }
    if (quality) {
      params.quality = quality;
    }

    return this.restService.rest().pipe(switchMap(rest =>
      rest.all('items').customGET('', params) as Observable<Page>));
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
    return this.restService.rest().pipe(switchMap(rest =>
      rest.all('items').getList(param) as Observable<Item[]>));
  }

  delete(item: any): Observable<any> {
    return this.restService.rest(item).pipe(switchMap(rest =>
      rest.remove().pipe(map(value => {
        ItemCacheService.decrementTotalElements();
        return value;
      }))));
  }
}
