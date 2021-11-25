import {Component, EventEmitter, Input, Output} from '@angular/core';
import {User} from '../../shared/user/user.interface';
import {PenInfo} from '../pen.interface';
import {PenWithContent} from '../pen-with-content.interface';

@Component({
  selector: 'app-pen-purchase',
  templateUrl: './pen-purchase.component.html',
  styleUrls: [
    './pen-purchase.component.sass'
  ]
})
export class PenPurchaseComponent {
  @Input() user: User;
  @Input() price: PenInfo;
  @Input() pens: PenWithContent[];

  @Output() penConstructEventEmitter: EventEmitter<void> = new EventEmitter<void>();

  construct() {
    this.penConstructEventEmitter.emit();
  }
}
