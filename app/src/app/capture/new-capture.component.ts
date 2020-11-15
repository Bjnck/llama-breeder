import {Component, EventEmitter, Inject, Input, Output} from "@angular/core";
import {Capture} from "./capture.interface";
import {CaptureService} from "./capture.service";

@Component({
  selector: 'new-capture',
  templateUrl: './new-capture.component.html',
})

export class NewCaptureComponent {
  @Input() activeCapture: Capture;

  @Output() captureFinishedEventEmitter: EventEmitter<void> = new EventEmitter<void>();

  timeLeft: number;
  interval;
  quality: number = 0;

  constructor(private captureService: CaptureService) {
  }

  ngOnInit() {
    this.setTimer(this.activeCapture);
  }

  startCapture() {
    this.captureService.createCapture(this.quality).subscribe(capture => {
      this.setTimer(capture);
      this.activeCapture = capture
    });
  }

  private setTimer(capture: Capture) {
    if (capture) {
      this.timeLeft = (new Date(capture.endTime).getTime() - new Date().getTime());
      this.interval = setInterval(() => {
        if (this.timeLeft > 0)
          this.timeLeft = this.timeLeft - 100;
        else
          this.finish()
      }, 100);
    }
  }

  finish() {
    clearInterval(this.interval);
    this.activeCapture = null;
    this.captureFinishedEventEmitter.emit();
  }

  setNest(quality: number) {
    if (this.quality == quality)
      this.quality = 0;
    else
      this.quality = quality;
  }
}
