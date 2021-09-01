import {Injectable} from "@angular/core";
import {Restangular} from "ngx-restangular";
import {Observable} from "rxjs";
import {ShopItem} from "./shop-item.interface";

@Injectable()
export class ShopItemService {

  baseRest = this.restangular.all('shop-items');

  constructor(private restangular: Restangular) {
  }

  list(): Observable<ShopItem[]> {
    return this.baseRest.getList({size: 100});
  }
}
