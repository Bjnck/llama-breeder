import {LoginService} from "./login/login.service";
import {Component} from "@angular/core";
import {User} from "../common/user/user.interface";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
})

export class HomeComponent {
  isLoggedIn = false;
  user: User;

  constructor(private loginService: LoginService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.user = this.route.snapshot.data.user;
    console.log(this.user);
    this.isLoggedIn = this.loginService.checkCredentials();
    let i = window.location.href.indexOf('access_token');
    if (!this.isLoggedIn && i != -1) {
      this.loginService.retrieveToken();
    }
  }
}
