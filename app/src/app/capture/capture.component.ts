import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {User} from '../shared/user/user.interface';
import {Capture} from './capture.interface';
import {CaptureService} from './capture.service';
import {AuthService} from '../shared/auth/auth.service';
import {HeaderService} from '../shared/header/header.service';
import {CaptureCreature} from './capture-creature.interface';

@Component({
  templateUrl: './capture.component.html',
  styleUrls: [
    '../shared/shared-style.sass',
    './capture.component.sass'
  ]
})

export class CaptureComponent implements OnInit {
  user: User;
  captureCreaturePairs: CaptureCreature[];

  constructor(private headerService: HeaderService,
              private authService: AuthService,
              private route: ActivatedRoute,
              private captureService: CaptureService) {
  }

  ngOnInit() {
    this.headerService.showHeader('Wild Lands', false);
    this.user = this.route.snapshot.data.user;
    this.captureCreaturePairs = this.route.snapshot.data.captures;
    // todo remove this when bait is managed by server
    this.captureCreaturePairs.forEach(pair => pair.capture.bait = 0);
  }

  get activeCapture(): Capture {
    return this.captureCreaturePairs
      .find(pair => new Date(pair.capture.endTime) >= new Date())?.capture;
  }

  get history(): CaptureCreature[] {
    return this.captureCreaturePairs
      .filter(pair => new Date(pair.capture.endTime) < new Date())
      .slice(0, 10);
  }

  onCaptureFinished() {
    this.captureService.listCaptures(11)
      .subscribe(pairs => this.captureCreaturePairs = pairs);
  }
}
