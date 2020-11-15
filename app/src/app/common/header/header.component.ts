import {Component, Input} from "@angular/core";
import {LoginService} from "../../home/login/login.service";
import {User} from "../user/user.interface";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent {
  @Input() isHome: boolean = false;
  @Input() title: string;
  @Input() user: User;

  constructor(private loginService: LoginService) { }

  logout() {
    this.loginService.logout();
  }
}
