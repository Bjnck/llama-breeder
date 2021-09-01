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

  add(code: string, quality: number): Observable<any> {
    return this.baseRest.post({code, quality});
  }

  count(): Observable<Page> {
    return this.baseRest.customGET('', {size: 1});
  }

  list(size: number, page: number, code: string): Observable<Item[]> {
    let param;
    if (code) {
      param = {size, page, code, sort: 'id,asc'};
    } else {
      param = {size, page, sort: 'id,asc'};
    }
    return this.baseRest.getList(param);
  }

  delete(item: any): Observable<any> {
    return item.remove();
  }
}
