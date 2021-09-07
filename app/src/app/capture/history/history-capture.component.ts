import {Component, Input} from '@angular/core';
import {CaptureCreature} from '../capture-creature.interface';

@Component({
  selector: 'app-history-capture',
  templateUrl: './history-capture.component.html',
  styleUrls: ['./history-capture.component.sass']
})
export class HistoryCaptureComponent {
  @Input() captureCreaturePairs: CaptureCreature[];

  constructor() {
  }
}
