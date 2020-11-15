import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {User} from "./user.interface";
import {Restangular} from "ngx-restangular";

@Injectable()
export class UserService {

  constructor(private restangular: Restangular) {
  }

  getUser(): Observable<User> {
    let baseUser = this.restangular.one('user');
    return baseUser.get();
  }
}
