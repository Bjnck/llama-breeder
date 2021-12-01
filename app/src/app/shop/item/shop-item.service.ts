import {Injectable} from '@angular/core';
import {Restangular} from 'ngx-restangular';
import {Observable, of} from 'rxjs';
import {ShopItem} from './shop-item.interface';
import {map} from 'rxjs/operators';

@Injectable()
export class ShopItemService {
  private items: ShopItem[];

  constructor(private restangular: Restangular) {
  }

  list(): Observable<ShopItem[]> {
    if (!this.items) {
      return this.restangular.all('shop-items').getList({size: 100})
        .pipe(map((items: ShopItem[]) => {
          this.items = items;
          return items;
        }));
    }
    return of(this.items);
  }
}
