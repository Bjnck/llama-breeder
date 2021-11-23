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
  showDeleteConfirmation = false;
  name: string;

  constructor(private headerService: HeaderService,
              private authService: AuthService,
              private userService: UserService,
              private route: ActivatedRoute) {
    this.headerService.showHeader('Account', false);
  }

  ngOnInit() {
    this.user = this.route.snapshot.data.user;
    this.name = this.user.name;
  }

  onNameChange(value: string) {
    if (value !== this.name) {
      this.name = value;
      this.user.name = value;
      this.userService.update(this.user)
        .subscribe({
          next: user => {
            this.invalidSubmit = false;
          },
          error: () => {
            this.invalidSubmit = true;
          }
        });
    }
  }

  logout() {
    this.authService.logout();
  }

  toggleDeleteConfirmation() {
    this.showDeleteConfirmation = !this.showDeleteConfirmation;
  }

  delete() {
    this.userService.delete(this.user);
    this.authService.delete();
  }
}
