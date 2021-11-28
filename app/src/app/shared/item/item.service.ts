import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Page} from '../page/page.interface';
import {Item} from './item.interface';
import {RestService} from '../rest/rest.service';

@Injectable()
export class ItemService {

  constructor(private restService: RestService) {
  }

  get(id: string, compute: boolean = true): Observable<Item> {
    return this.restService.rest().one('items', id).get({compute});
  }

  add(code: string, quality: number): Observable<any> {
    return this.restService.rest().all('items').post({code, quality});
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

  list(size: number, page: number, code: string, compute: boolean = true): Observable<Item[]> {
    let param;
    if (code) {
      param = {size, page, code, sort: 'id,asc', compute};
    } else {
      param = {size, page, sort: 'id,asc', compute};
    }
    return this.restService.rest().all('items').getList(param);
  }

  delete(item: any): Observable<any> {
    return this.restService.rest(item).remove();
  }
}
