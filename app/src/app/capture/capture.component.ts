import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {User} from '../shared/user/user.interface';
import {Capture} from './capture.interface';
import {CaptureService} from './capture.service';
import {AuthService} from '../shared/auth/auth.service';
import {HeaderService} from '../shared/header/header.service';
import {UserService} from '../shared/user/user.service';

@Component({
  templateUrl: './capture.component.html',
  styleUrls: [
    '../shared/shared-style.sass',
    './capture.component.sass'
  ]
})

export class CaptureComponent implements OnInit {
  user: User;
  captures: Capture[];

  constructor(private headerService: HeaderService,
              private authService: AuthService,
              private route: ActivatedRoute,
              private captureService: CaptureService,
              private userService: UserService) {
  }

  ngOnInit() {
    this.headerService.showHeader('Wild Lands', false);
    this.user = this.route.snapshot.data.user;
    this.captures = this.route.snapshot.data.captures;
    // todo remove this when bait is managed by server
    this.captures.forEach(capture => capture.bait = 0);
  }

  get activeCapture(): Capture {
    return this.captures.find(capture => !capture.sex);
  }

  get history(): Capture[] {
    return this.captures.filter(capture => capture.sex).slice(0, 10);
  }

  onCaptureFinished() {
    this.captureService.listCaptures(11)
      .subscribe(captures => {
        this.captures = captures;

        // update user level if tutorial ended
        if (this.user.level <= 0) {
          this.userService.fetch();
        }
      });
  }
}
