import {Inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Capture} from './capture.interface';
import {REST_FULL_RESPONSE} from '../shared/rest/restangular.custom';
import {flatMap, switchMap} from 'rxjs/operators';
import {RestService} from '../shared/rest/rest.service';
import {ItemCacheService} from '../shared/item/item-cache.service';

@Injectable()
export class CaptureService {

  constructor(@Inject(REST_FULL_RESPONSE) public restFullResponse,
              private restService: RestService) {
  }

  list(size: number): Observable<Capture[]> {
    return this.restService.rest().pipe(switchMap(rest =>
      rest.all('captures').getList({size}) as Observable<Capture[]>));
  }

  create(quality: number): Observable<Capture> {
    return this.restService.restFull().pipe(switchMap(restfull =>
      restfull.all('captures').post({quality}).pipe(flatMap((response: any) => {
        if (quality > 0) {
          ItemCacheService.decrementTotalElements();
        }
        return this.restService.rest().pipe(switchMap(rest =>
          rest.oneUrl('captures', response.headers.headers.get('location')).get()));
      })) as Observable<Capture>));
  }

  redeem(id: string): Observable<Capture> {
    return this.restService.rest().pipe(switchMap(rest =>
      rest.all('captures').customPOST({}, id + '/action/redeem') as Observable<Capture>));
  }
}
