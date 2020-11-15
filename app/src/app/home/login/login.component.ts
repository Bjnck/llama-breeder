import {Component} from "@angular/core";
import {LoginService} from "./login.service";

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
})

export class LoginComponent {

  constructor(private loginService: LoginService) { }

  login() {
    this.loginService.login();
  }

}
