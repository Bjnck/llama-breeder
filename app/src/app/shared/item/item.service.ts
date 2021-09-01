import {Injectable} from "@angular/core";
import {Restangular} from "ngx-restangular";
import {Observable} from "rxjs";
import {Page} from "../page/page.interface";
import {Item} from "./item.interface";

@Injectable()
export class ItemService {

  baseRest = this.restangular.all('items');

  constructor(private restangular: Restangular) {
  }

  buy(code: string, quality: number): Observable<any> {
    return this.baseRest.post({code: code, quality: quality});
  }

  count(): Observable<Page> {
    return this.baseRest.customGET("", {size: 1});
  }

  list(size: number): Observable<Item[]> {
    return this.baseRest.getList({size: size});
  }
}
