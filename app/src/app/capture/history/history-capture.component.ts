import {Component, Input} from '@angular/core';
import {Capture} from '../capture.interface';

@Component({
  selector: 'app-history-capture',
  templateUrl: './history-capture.component.html',
  styleUrls: ['./history-capture.component.sass']
})
export class HistoryCaptureComponent {
  @Input() captures: Capture[];

  constructor() {
  }
}
