import {Component, Input} from "@angular/core";
import {Capture} from "./capture.interface";

@Component({
  selector: 'capture-history',
  templateUrl: './capture-history.component.html',
})

export class CaptureHistoryComponent {
  @Input() captures : Capture[];

  constructor() {
  }
}
