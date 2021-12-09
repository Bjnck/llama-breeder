import {Inject, Injectable} from '@angular/core';
import {Restangular} from 'ngx-restangular';
import {AuthService} from '../auth/auth.service';
import {REST_FULL_RESPONSE} from './restangular.custom';
import {from, Observable} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable()
export class RestService {

  constructor(private restangular: Restangular,
              @Inject(REST_FULL_RESPONSE) private restFullResponse,
              private authService: AuthService) {
  }

  rest(elt?: any): Observable<any> {
    if (elt) {
      if (!elt.reqParams) {
        elt.reqParams = {headers: {}};
      }
      return from(this.authService.getUser().getIdToken()).pipe(map((token: string) => {
        elt.reqParams.headers = {Authorization: token};
        return elt;
      }));
    } else {
      return from(this.authService.getUser().getIdToken()).pipe(map((token: string) => {
        this.restangular.provider.setDefaultHeaders({Authorization: token});
        return this.restangular;
      }));
    }
  }

  restFull(): Observable<any> {
    return from(this.authService.getUser().getIdToken()).pipe(map((token: string) => {
      this.restFullResponse.setDefaultHeaders({Authorization: token});
      return this.restFullResponse;
    }));
  }
}
