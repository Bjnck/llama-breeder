import {Component, OnInit} from '@angular/core';
import {AuthService} from '../shared/auth/auth.service';
import {HeaderService} from '../shared/header/header.service';

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {
  color: string;

  constructor(private headerService: HeaderService,
              private authService: AuthService) {
    this.headerService.hideHeader();
  }

  ngOnInit(): void {
    const random = this.randomIntFromInterval(1, 3);
    console.log(random)
    if (random === 1) {
      this.color = 'crimson';
    } else if (random === 2) {
      this.color = 'forestgreen';
    } else if (random === 3) {
      this.color = 'cornflowerblue';
    }
  }

  // min and max included
  private randomIntFromInterval(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min);
  }

  login() {
    this.authService.login();
  }
}
