import {EventEmitter, Injectable, Output} from "@angular/core";
import {User} from "./user.interface";
import {Restangular} from "ngx-restangular";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";

@Injectable()
export class UserService {

  @Output() userChangeEventEmitter: EventEmitter<any> = new EventEmitter<any>();

  private user;

  constructor(private restangular: Restangular) {
  }

  fetch(): Promise<User> {
    return this.restangular.one('user').get()
      .pipe(map(user => {
        this.user = user;
        this.userChangeEventEmitter.emit(this.user);
        return user;
      }))
      .toPromise();
  }

  update(user: any): Observable<User> {
    this.user = user;
    this.userChangeEventEmitter.emit(this.user);
    return user.put();
  }

  updateCoins(coins: number) {
    this.user.coins = coins;
    this.userChangeEventEmitter.emit(this.user);
  }

  delete(user: any) {
    this.user = null;
    this.userChangeEventEmitter.emit(this.user);
    user.remove();
  }
}
