import {Inject, Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve} from "@angular/router";
import {Observable} from "rxjs";
import {User} from "./user.interface";
import {UserService} from "./user.service";
import {LoginService} from "../../home/login/login.service";

@Injectable()
export class UserResolve implements Resolve<User> {

  constructor(private userService: UserService,
              private loginService: LoginService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<User> {
    if (this.loginService.checkCredentials())
      return this.userService.getUser();
    else
      return null;
  }
}
