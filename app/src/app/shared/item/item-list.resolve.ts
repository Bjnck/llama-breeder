import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {ItemService} from './item.service';
import {Item} from './item.interface';
import {Observable} from 'rxjs';

@Injectable()
export class ItemListResolve implements Resolve<Item[]> {

  constructor(private itemService: ItemService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Item[]> {
    return this.itemService.list(20, 0);
  }
}
