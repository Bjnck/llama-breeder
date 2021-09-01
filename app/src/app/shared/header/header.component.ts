import {Component, Input} from "@angular/core";
import {User} from "../user/user.interface";
import {UserService} from "../user/user.service";
import {AuthService} from "../auth/auth.service";
import {HeaderService} from "./header.service";
import {Header} from "./header.interface";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass']
})
export class HeaderComponent {
  data: Header = new class implements Header {
    display: boolean = true;
    isHome: boolean = true;
    title: String = "Llama Breeder";
  };
  user: User;

  constructor(private authService: AuthService,
              private headerService: HeaderService,
              private userService: UserService) {
    this.headerService.headerChangeEventEmitter.subscribe(data => this.data = data)
    this.userService.userChangeEventEmitter.subscribe(user => this.user = user);
  }

  logout() {
    this.authService.logout();
  }
}
