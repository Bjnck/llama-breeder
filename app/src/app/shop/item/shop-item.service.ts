import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {ShopItem} from './shop-item.interface';
import {map, switchMap} from 'rxjs/operators';
import {RestService} from '../../shared/rest/rest.service';

@Injectable()
export class ShopItemService {
  private items: ShopItem[];

  constructor(private restService: RestService) {
  }

  list(): Observable<ShopItem[]> {
    if (!this.items) {
      return this.restService.rest().pipe(switchMap(rest =>
        rest.all('shop-items').getList({size: 100}).pipe(map((items: ShopItem[]) => {
          this.items = items;
          return items;
        })) as Observable<ShopItem[]>));
    }
    return of(this.items);
  }
}
