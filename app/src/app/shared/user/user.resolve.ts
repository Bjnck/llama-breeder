import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {User} from './user.interface';
import {UserService} from './user.service';

@Injectable()
export class UserResolve implements Resolve<User> {

  constructor(private userService: UserService) {
  }

  resolve(route: ActivatedRouteSnapshot): Promise<User> {
    if (this.userService.get()) {
      return new Promise((resolve, reject) => {
        resolve(this.userService.get());
      });
    }

    return this.userService.fetch();
  }
}
