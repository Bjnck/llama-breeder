import {Component} from "@angular/core";
import {User} from "../shared/user/user.interface";
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../shared/auth/auth.service";
import {HeaderService} from "../shared/header/header.service";
import {UserService} from "../shared/user/user.service";

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})

export class HomeComponent {
  user: User;

  constructor(private headerService: HeaderService,
    private userService: UserService,
    private authService: AuthService,
              private route: ActivatedRoute) {
    this.headerService.showHeader("Llama Breeder", true);
  }

  ngOnInit() {
    this.user = this.route.snapshot.data.user;
  }
}
