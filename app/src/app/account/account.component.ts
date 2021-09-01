import {Component} from "@angular/core";
import {User} from "../shared/user/user.interface";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../shared/user/user.service";
import {AuthService} from "../shared/auth/auth.service";
import {HeaderService} from "../shared/header/header.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.sass']
})

export class AccountComponent {
  user: User;
  invalidSubmit: boolean = false;
  validSubmit: boolean = false;
  showDeleteConfirmation: boolean = false;

  private updateSubscription: Subscription;

  constructor(private headerService: HeaderService,
              private authService: AuthService,
              private userService: UserService,
              private route: ActivatedRoute) {
    this.headerService.showHeader("Account", false);
  }

  ngOnInit() {
    this.user = this.route.snapshot.data.user;
  }

  ngOnDestroy() {
    if (this.updateSubscription)
      this.updateSubscription.unsubscribe();
  }

  submit() {
    this.updateSubscription = this.userService.update(this.user)
      .subscribe(
        (user: User) => {
          this.invalidSubmit = false;
          this.validSubmit = true;
        },
        err => {
          this.invalidSubmit = true;
          this.validSubmit = false;
        });
  }

  toggleDeleteConfirmation() {
    this.showDeleteConfirmation = !this.showDeleteConfirmation;
  }

  delete() {
    this.userService.delete(this.user);
    this.authService.logout();
  }
}
