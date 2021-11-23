import {Inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Capture} from './capture.interface';
import {REST_FULL_RESPONSE} from '../shared/rest/restangular.custom';
import {flatMap} from 'rxjs/operators';
import {RestService} from '../shared/rest/rest.service';

@Injectable()
export class CaptureService {

  constructor(@Inject(REST_FULL_RESPONSE) public restFullResponse,
              private restService: RestService) {
  }

  list(size: number): Observable<Capture[]> {
    return this.restService.rest().all('captures').getList({size});
  }

  create(quality: number): Observable<Capture> {
    return this.restService.restFull().all('captures').post({quality})
      .pipe(flatMap((response: any) =>
        this.restService.rest().oneUrl('captures', response.headers.headers.get('location')).get()));
  }

  redeem(id: string): Observable<Capture> {
    return this.restService.rest().all('captures').customPOST({}, id + '/action/redeem');
  }
}
