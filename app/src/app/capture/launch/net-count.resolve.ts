import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {ItemService} from '../../shared/item/item.service';
import {NetCount} from './net-count.interface';

@Injectable()
export class NetCountResolve implements Resolve<NetCount> {

  constructor(private itemService: ItemService) {
  }

  async resolve(route: ActivatedRouteSnapshot): Promise<NetCount> {
    const netCount: NetCount = {quality_1: 0, quality_2: 0, quality_3: 0};

    const promises: Promise<any>[] = [];
    promises.push(this.itemService.count('net', 1, false)
      .toPromise()
      .then(page => netCount.quality_1 = page.totalElements));
    promises.push(this.itemService.count('net', 2, false)
      .toPromise()
      .then(page => netCount.quality_2 = page.totalElements));
    promises.push(this.itemService.count('net', 3, false)
      .toPromise()
      .then(page => netCount.quality_3 = page.totalElements));

    await Promise.all(promises);

    return new Observable<NetCount>(observer => {
      observer.next(netCount);
      observer.complete();
    }).toPromise();
  }
}
