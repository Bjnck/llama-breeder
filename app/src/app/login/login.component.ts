import {Component} from "@angular/core";
import {AuthService} from "../shared/auth/auth.service";
import {HeaderService} from "../shared/header/header.service";

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})

export class LoginComponent {

  constructor(private headerService: HeaderService,
              private authService: AuthService) {
    this.headerService.hideHeader();
  }

  login() {
    this.authService.login();
  }

}
