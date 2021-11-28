import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {User} from './user.interface';
import {UserService} from './user.service';
import {Observable} from 'rxjs';

@Injectable()
export class UserResolve implements Resolve<Observable<User>> {

  constructor(private userService: UserService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<User> {
    return this.userService.fetch();
  }
}
