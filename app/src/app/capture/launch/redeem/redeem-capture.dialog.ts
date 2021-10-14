import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {CaptureService} from '../../capture.service';
import {Capture} from '../../capture.interface';

@Component({
  templateUrl: './redeem-capture.dialog.html',
  styleUrls: [
    './redeem-capture.dialog.sass'
  ]
})
export class RedeemCaptureDialogComponent implements OnInit, OnDestroy {
  capture: Capture;

  interval;
  step = 0;

  constructor(@Inject(MAT_DIALOG_DATA) public data: Capture,
              private captureService: CaptureService) {
    this.capture = data;
  }

  ngOnInit(): void {
    this.setAnimation();
    this.captureService.redeem(this.capture.id).subscribe({
      next: capture => this.capture = capture
    });
  }

  ngOnDestroy(): void {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

  private setAnimation() {
    this.interval = setInterval(() => {
      this.step++;
      if (this.step > 3) {
        clearInterval(this.interval);
      }
    }, 800);
  }


}
