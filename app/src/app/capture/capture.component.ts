import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {User} from '../shared/user/user.interface';
import {Capture} from './capture.interface';
import {CaptureService} from './capture.service';
import {AuthService} from '../shared/auth/auth.service';
import {HeaderService} from '../shared/header/header.service';
import {UserService} from '../shared/user/user.service';
import {TimerUtil} from '../shared/timer/timer.util';

@Component({
  templateUrl: './capture.component.html',
  styleUrls: [
    '../shared/shared-style.sass',
    './capture.component.sass'
  ]
})

export class CaptureComponent implements OnInit {
  user: User;
  activeCapture: Capture;
  history: Capture[];
  redeem = false;

  constructor(private headerService: HeaderService,
              private authService: AuthService,
              private route: ActivatedRoute,
              private userService: UserService) {
  }

  ngOnInit() {
    this.headerService.showHeader('Wild Lands', false);
    this.user = this.route.snapshot.data.user;
    const captures: Capture[] = this.route.snapshot.data.captures;
    // todo remove this when bait is managed by server
    captures.forEach(capture => capture.bait = 0);

    this.activeCapture = captures.find(capture => !capture.sex);
    if (this.activeCapture) {
      this.redeem = TimerUtil.utc(new Date(this.activeCapture.endTime)) < TimerUtil.utc(new Date());
    }
    this.history = captures.filter(capture => capture.sex).slice(0, 10);
  }

  onCaptureStarted(capture: Capture) {
    this.activeCapture = capture;
  }

  onCaptureFinished() {
    this.redeem = true;
  }

  onRedeem(capture: Capture) {
    this.activeCapture = null;
    this.redeem = false;

    const newHistory = this.history.reverse();
    newHistory.push(capture);
    this.history = newHistory.reverse();

    // todo we can use cache for user and directly change level to 1
    // update user level if tutorial ended
    if (this.user.level <= 0 && this.history.length >= 3) {
      this.userService.fetch();
    }
  }
}
