import {Component, OnInit} from '@angular/core';
import {User} from '../shared/user/user.interface';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../shared/user/user.service';
import {AuthService} from '../shared/auth/auth.service';
import {HeaderService} from '../shared/header/header.service';

@Component({
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.sass']
})

export class AccountComponent implements OnInit {
  user: User;
  invalidSubmit = false;
  validSubmit = false;
  showDeleteConfirmation = false;

  constructor(private headerService: HeaderService,
              private authService: AuthService,
              private userService: UserService,
              private route: ActivatedRoute) {
    this.headerService.showHeader('Account', false);
  }

  ngOnInit() {
    this.user = this.route.snapshot.data.user;
  }

  submit() {
    this.userService.update(this.user)
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
