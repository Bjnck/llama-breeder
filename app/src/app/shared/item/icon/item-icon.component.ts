import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-item-icon',
  templateUrl: './item-icon.component.html',
  styleUrls: ['./item-icon.component.sass']
})

export class ItemIconComponent {
  @Input() code: string;
}
