import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Capture} from '../capture.interface';
import {CaptureService} from '../capture.service';
import {ActivatedRoute} from '@angular/router';
import {NetCount} from './net-count.interface';
import {TimerUtil} from '../../shared/timer/timer.util';
import {MatDialog} from '@angular/material/dialog';
import {RedeemCaptureDialogComponent} from './redeem/redeem-capture.dialog';

@Component({
  selector: 'app-launch-capture',
  templateUrl: './launch-capture.component.html',
  styleUrls: ['./launch-capture.component.sass']
})
export class LaunchCaptureComponent implements OnInit, OnDestroy {

  @Input() capture: Capture;
  @Input() redeem;

  // todo get creature count as input, if >= 250(constant) then display error message in help (probably in red) and block redeem button

  netCount: NetCount;

  timeLeft: number;
  interval;
  quality = 0;

  @Output() captureStarted: EventEmitter<Capture> = new EventEmitter<Capture>();
  @Output() captureFinished: EventEmitter<void> = new EventEmitter<void>();
  @Output() redeemEvent: EventEmitter<Capture> = new EventEmitter<Capture>();

  constructor(private captureService: CaptureService,
              private route: ActivatedRoute,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    // todo use this with a cache, for each buy net update cache and remove when used
    this.netCount = this.route.snapshot.data.netCount;
    this.setTimer(this.capture);
  }

  ngOnDestroy() {
    clearInterval(this.interval);
  }

  launch() {
    this.captureService.create(this.quality)
      .subscribe(capture => {
        this.setTimer(capture);
        // this.capture = capture;
        this.captureStarted.emit(capture);

        // todo use cache service for net count (+ update cache when shop net)
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
    if (capture && TimerUtil.utc(new Date(capture.endTime)) >= TimerUtil.utc(new Date())) {
      this.timeLeft = Math.max(0, TimerUtil.timeLeft(new Date(capture.endTime)));
      this.interval = setInterval(() => {
        if (this.timeLeft > 0) {
          this.timeLeft = Math.max(0, TimerUtil.timeLeft(new Date(capture.endTime)));
        } else {
          this.finish();
        }
      }, 100);
    }
  }

  finish() {
    this.captureFinished.emit();
    clearInterval(this.interval);
  }

  setNet(quality: number) {
    if (this.quality === quality) {
      this.quality = 0;
    } else {
      this.quality = quality;
    }
  }

  redeemCapture() {
    this.dialog.open(RedeemCaptureDialogComponent, {
      disableClose: true,
      data: this.capture,
      position: {top: '50px'},
      width: '100%',
      maxWidth: '500px',
      minWidth: '340px',
      restoreFocus: false
    }).afterClosed().subscribe({
      next: (capture: Capture) => {
        if (capture.sex) {
          this.redeemEvent.emit(capture);
        }
      }
    });
  }

}
