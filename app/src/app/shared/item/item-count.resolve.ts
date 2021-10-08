import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {Page} from '../page/page.interface';
import {ItemService} from './item.service';

@Injectable()
export class ItemCountResolve implements Resolve<Page> {

  constructor(private itemService: ItemService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Page> {
    return this.itemService.count(null, null, false);
  }
}
