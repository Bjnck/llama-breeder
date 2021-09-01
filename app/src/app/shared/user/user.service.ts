import {EventEmitter, Injectable, Output} from "@angular/core";
import {User} from "./user.interface";
import {Restangular} from "ngx-restangular";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";

@Injectable()
export class UserService {

  @Output() userChangeEventEmitter: EventEmitter<any> = new EventEmitter<any>();

  private _user;

  constructor(private restangular: Restangular) {
  }

  fetch(): Promise<User> {
    return this.restangular.one('user').get()
      .pipe(map(user => {
        this._user = user;
        this.userChangeEventEmitter.emit(this._user);
        return user;
      }))
      .toPromise();
  }

  get(): User {
    return this._user;
  }

  update(user: any): Observable<User> {
    this._user = user;
    this.userChangeEventEmitter.emit(this._user);
    return user.put();
  }

  updateCoins(coins: number){
    this._user.coins = coins;
    this.userChangeEventEmitter.emit(this._user);
  }

  delete(user: any) {
    this._user = null;
    this.userChangeEventEmitter.emit(this._user);
    user.remove();
  }
}