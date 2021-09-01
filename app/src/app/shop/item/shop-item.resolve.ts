import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {ShopItem} from './shop-item.interface';
import {ShopItemService} from './shop-item.service';

@Injectable()
export class ShopItemResolve implements Resolve<ShopItem[]> {

  constructor(private shopItemService: ShopItemService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<ShopItem[]> {
      return this.shopItemService.list();

  }
}
