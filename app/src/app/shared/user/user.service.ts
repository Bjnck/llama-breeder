import {EventEmitter, Injectable, Output} from '@angular/core';
import {User} from './user.interface';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {RestService} from '../rest/rest.service';

@Injectable()
export class UserService {
  private user;

  @Output() userChangeEventEmitter: EventEmitter<any> = new EventEmitter<any>();

  constructor(private restService: RestService) {
  }

  fetch(): Observable<User> {
    return this.restService.rest().one('user').get()
      .pipe(map(user => {
        this.user = user;
        this.userChangeEventEmitter.emit(this.user);
        return user;
      }));
  }

  update(user: any): Observable<User> {
    this.user = user;
    this.userChangeEventEmitter.emit(this.user);
    return this.restService.rest(user).put();
  }

  updateCoins(coins: number) {
    this.user.coins = coins;
    this.userChangeEventEmitter.emit(this.user);
  }

  delete(user: any) {
    this.user = null;
    this.userChangeEventEmitter.emit(this.user);
    return this.restService.rest(user).remove();
  }
}
