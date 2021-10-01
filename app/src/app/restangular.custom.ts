import {InjectionToken} from '@angular/core';

import {Restangular} from 'ngx-restangular';
import {Cookie} from 'ng2-cookies';
import {environment} from '../environments/environment';

export function RestangularConfigFactory(RestangularProvider) {
  RestangularProvider.setBaseUrl(environment.serverUrl);
  RestangularProvider.setDefaultHeaders({Authorization: 'Bearer ' + Cookie.get('access_token')});
  RestangularProvider.addResponseInterceptor((data, operation, what, url, response) => {
    // this is a count
    if (operation === 'get' && data.page && data.page.size === 1) {
      return data.page;
    }

    if (operation === 'getList') {
      if (!data._embedded) {
        return [];
      }
      const dataModified = data._embedded[what];
      dataModified._links = data._links;
      data = dataModified;
    }
    return data;
  });
}

export const REST_FULL_RESPONSE = new InjectionToken<any>('RestFullResponse');

export function RestFullResponseFactory(restangular: Restangular) {
  return restangular.withConfig((RestangularConfigurer) => {
    RestangularConfigurer.setFullResponse(true);
  });
}
