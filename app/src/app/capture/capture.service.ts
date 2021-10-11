import {Inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Restangular} from 'ngx-restangular';
import {Capture} from './capture.interface';
import {REST_FULL_RESPONSE} from '../restangular.custom';
import {flatMap} from 'rxjs/operators';

@Injectable()
export class CaptureService {

  baseRest = this.restangular.all('captures');

  constructor(@Inject(REST_FULL_RESPONSE) public restFullResponse,
              private restangular: Restangular) {
  }

  listCaptures(size: number): Observable<Capture[]> {
    return this.baseRest.getList({size});
  }

  createCapture(quality: number): Observable<Capture> {
    return this.restFullResponse.all('captures').post({quality})
      .pipe(flatMap((response: any) =>
        this.baseRest.oneUrl('captures', response.headers.headers.get('location')).get()));
  }
}
