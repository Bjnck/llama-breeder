import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Capture} from '../capture.interface';
import {CaptureService} from '../capture.service';
import {ActivatedRoute} from '@angular/router';
import {NetCount} from './net-count.interface';
import {TimerUtil} from '../../shared/timer/timer.util';

@Component({
  selector: 'app-launch-capture',
  templateUrl: './launch-capture.component.html',
  styleUrls: ['./launch-capture.component.sass']
})
export class LaunchCaptureComponent implements OnInit, OnDestroy {

  active: Capture;

  @Input()
  set activeCapture(capture: Capture) {
    this.active = capture;
    if (capture) {
      this.maxCreatureReached = TimerUtil.utc(new Date(capture.endTime)) < TimerUtil.utc(new Date());
    }
  }

  @Output() captureFinishedEventEmitter: EventEmitter<void> = new EventEmitter<void>();

  netCount: NetCount;

  maxCreatureReached = false;
  timeLeft: number;
  interval;
  quality = 0;
  bait = 0;

  constructor(private captureService: CaptureService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.netCount = this.route.snapshot.data.netCount;
    this.setTimer();
  }

  ngOnDestroy() {
    clearInterval(this.interval);
  }

  launch() {
    this.captureService.createCapture(this.quality)
      .subscribe(capture => {
        // todo remove this when server manage bait
        capture.bait = 0;

        this.activeCapture = capture;

        this.setTimer();

        if (this.quality === 1) {
          this.netCount.quality_1--;
        } else if (this.quality === 2) {
          this.netCount.quality_2--;
        } else if (this.quality === 3) {
          this.netCount.quality_3--;
        }

        this.quality = 0;
      });
  }

  private setTimer() {
    if (this.active && TimerUtil.utc(new Date(this.active.endTime)) >= TimerUtil.utc(new Date())) {
      this.timeLeft = TimerUtil.timeLeft(new Date(this.active.endTime));
      this.interval = setInterval(() => {
        if (this.timeLeft > 0) {
          this.timeLeft = TimerUtil.timeLeft(new Date(this.active.endTime));
          // if (this.timeLeft > 100) {
          //   this.timeLeft = this.timeLeft - 100;
          // } else {
          //   this.timeLeft = 0;
          // }
        } else {
          this.finish();
        }
      }, 100);
    }
  }

  finish() {
    this.activeCapture = null;
    clearInterval(this.interval);
    this.captureFinishedEventEmitter.emit();
  }

  setNet(quality: number) {
    if (this.quality === quality) {
      this.quality = 0;
    } else {
      this.quality = quality;
    }
  }
}
