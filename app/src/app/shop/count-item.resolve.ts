import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve} from "@angular/router";
import {Observable} from "rxjs";
import {ItemService} from "../shared/item/item.service";
import {Page} from "../shared/page/page.interface";

@Injectable()
export class CountItemResolve implements Resolve<Page> {

  constructor(private itemService: ItemService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Page> {
    return this.itemService.count();
  }
}
