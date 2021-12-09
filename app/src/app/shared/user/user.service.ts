import {EventEmitter, Injectable, Output} from '@angular/core';
import {User} from './user.interface';
import {map, switchMap} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {RestService} from '../rest/rest.service';
import {Restangular} from 'ngx-restangular';

@Injectable()
export class UserService {
  private user;

  @Output() userChangeEventEmitter: EventEmitter<any> = new EventEmitter<any>();

  constructor(private restService: RestService) {
  }

  fetch(): Observable<User> {
    return this.restService.rest().pipe(switchMap(rest => {
      return rest.one('user').get().pipe(map(user => {
        this.user = user;
        this.userChangeEventEmitter.emit(this.user);
        return user;
      })) as Observable<User>;
    }));
  }

  update(user: any): Observable<any> {
    this.user = user;
    this.userChangeEventEmitter.emit(this.user);
    return this.restService.rest(user).pipe(switchMap((rest: Restangular) => rest.put()));
  }

  updateCoins(coins: number) {
    this.user.coins = coins;
    this.userChangeEventEmitter.emit(this.user);
  }

  updateLevel(level: number) {
    this.user.level = level;
    this.userChangeEventEmitter.emit(this.user);
  }

  delete(user: any): Observable<any> {
    this.user = null;
    this.userChangeEventEmitter.emit(this.user);
    return this.restService.rest(user).pipe(switchMap((rest: Restangular) => rest.remove()));
  }
}
