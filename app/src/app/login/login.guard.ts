import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router";
import {AuthService} from "../shared/auth/auth.service";

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate() {
    let isFetchingToken = window.location.href.indexOf('access_token') != -1;
    if (isFetchingToken) {
      this.authService.retrieveToken(window.location.href);
    }

    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/']);
    }

    return true;
  }
}
