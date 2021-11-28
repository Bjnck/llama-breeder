import {Inject, Injectable} from '@angular/core';
import {Restangular} from 'ngx-restangular';
import {AuthService} from '../auth/auth.service';
import {REST_FULL_RESPONSE} from './restangular.custom';

@Injectable()
export class RestService {

  constructor(private restangular: Restangular,
              @Inject(REST_FULL_RESPONSE) private restFullResponse,
              private authService: AuthService) {
  }

  rest(elt?: any): any {
    if (elt) {
      if (!elt.reqParams) {
        elt.reqParams = {headers: {}};
      }
      elt.reqParams.headers = {Authorization: this.authService.getTokenId()};
      return elt;
    } else {
      this.restangular.provider.setDefaultHeaders({Authorization: this.authService.getTokenId()});
      return this.restangular;
    }
  }

  restFull(): any {
    return this.restFullResponse.setDefaultHeaders({Authorization: this.authService.getTokenId()});
  }
}
