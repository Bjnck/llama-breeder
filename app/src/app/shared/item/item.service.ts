import {Injectable} from '@angular/core';
import {Restangular} from 'ngx-restangular';
import {Observable} from 'rxjs';
import {Page} from '../page/page.interface';
import {Item} from './item.interface';

@Injectable()
export class ItemService {

  baseRest = this.restangular.all('items');

  constructor(private restangular: Restangular) {
  }

  get(id: string, compute: boolean = true): Observable<Item> {
    return this.restangular.one('items', id).get({compute});
  }

  add(code: string, quality: number): Observable<any> {
    return this.baseRest.post({code, quality});
  }

  count(code?: string, quality?: number, compute: boolean = true): Observable<Page> {
    const params: any = {size: 1, compute};
    if (code) {
      params.code = code;
    }
    if (quality) {
      params.quality = quality;
    }

    return this.baseRest.customGET('', params);
  }

  list(size: number, page: number, code: string, compute: boolean = true): Observable<Item[]> {
    let param;
    if (code) {
      param = {size, page, code, sort: 'id,asc', compute};
    } else {
      param = {size, page, sort: 'id,asc', compute};
    }
    return this.baseRest.getList(param);
  }

  delete(item: any): Observable<any> {
    return item.remove();
  }
}
