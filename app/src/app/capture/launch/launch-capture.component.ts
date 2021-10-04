import {Component, EventEmitter, Inject, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Capture} from '../capture.interface';
import {CaptureService} from '../capture.service';
import {ActivatedRoute} from '@angular/router';
import {NetCount} from './net-count.interface';

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
      this.maxCreatureReached = this.utc(new Date(capture.endTime)) < this.utc(new Date());
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
    this.setTimer(this.active);
  }

  ngOnDestroy() {
    clearInterval(this.interval);
  }

  launch() {
    this.captureService.createCapture(this.quality)
      .subscribe(capture => {
        this.setTimer(capture);
        // todo remove this when server manage bait
        capture.bait = 0;

        this.activeCapture = capture;

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

  private setTimer(capture: Capture) {
    if (capture && this.utc(new Date(capture.endTime)) >= this.utc(new Date())) {
      this.timeLeft = (this.utc(new Date(capture.endTime)).getTime() - this.utc(new Date()).getTime());
      this.interval = setInterval(() => {
        if (this.timeLeft > 0) {
          if (this.timeLeft > 100) {
            this.timeLeft = this.timeLeft - 100;
          } else {
            this.timeLeft = 0;
          }
        } else {
          this.finish();
        }
      }, 100);
    }
  }

  private utc(date: Date): Date {
    const utc = Date.UTC(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(),
      date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds());

    return new Date(utc);
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
