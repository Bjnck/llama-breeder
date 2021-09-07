import {Component, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {Capture} from '../capture.interface';
import {CaptureService} from '../capture.service';
import {ActivatedRoute} from '@angular/router';
import {NetCount} from './net-count.interface';

@Component({
  selector: 'app-launch-capture',
  templateUrl: './launch-capture.component.html',
  styleUrls: ['./launch-capture.component.sass']
})

export class LaunchCaptureComponent implements OnInit {
  @Input() activeCapture: Capture;

  @Output() captureFinishedEventEmitter: EventEmitter<void> = new EventEmitter<void>();

  netCount: NetCount;

  timeLeft: number;
  interval;
  quality = 0;
  bait = 0;

  constructor(private captureService: CaptureService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.netCount = this.route.snapshot.data.netCount;
    this.setTimer(this.activeCapture);
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
    if (capture) {
      this.timeLeft = (new Date(capture.endTime).getTime() - new Date().getTime());
      this.interval = setInterval(() => {
        if (this.timeLeft > 0) {
          this.timeLeft = this.timeLeft - 100;
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
