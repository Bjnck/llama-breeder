import {InjectionToken} from '@angular/core';

import {Restangular} from 'ngx-restangular';
import {environment} from '../../../environments/environment';
import {CreatureCacheService} from '../creature/creature-cache.service';
import {ItemCacheService} from '../item/item-cache.service';

export function RestangularConfigFactory(RestangularProvider) {
  RestangularProvider.setBaseUrl(environment.serverUrl);

  RestangularProvider.addResponseInterceptor((data, operation, what, url, response) => {
    // this is a count
    if (operation === 'get' && data.page && data.page.size === 1) {
      return data.page;
    }

    if (operation === 'getList') {
      if (what === 'creatures') {
        CreatureCacheService.setTotalElements(data.page.totalElements);
        CreatureCacheService.setFilterElements(data.page.totalElements);
      }
      if (what === 'items') {
        ItemCacheService.setTotalElements(data.page.totalElements);
        ItemCacheService.setFilterElements(data.page.totalElements);
      }
      if (what === 'pens/info' || what === 'creatures/info') {
        return data;
      }

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
