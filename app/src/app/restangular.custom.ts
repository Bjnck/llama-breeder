import {InjectionToken} from '@angular/core';

import {Restangular} from "ngx-restangular";
import {Cookie} from "ng2-cookies";

export function RestangularConfigFactory(RestangularProvider) {
  RestangularProvider.setBaseUrl('http://localhost:8080');
  RestangularProvider.setDefaultHeaders({'Authorization': 'Bearer ' + Cookie.get('access_token')});
  RestangularProvider.addResponseInterceptor((data, operation, what, url, response) => {
    if (operation == 'getList') {
      if (!data._embedded)
        return [];
      let dataModified = data._embedded[what];
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
