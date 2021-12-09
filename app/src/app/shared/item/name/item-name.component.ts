import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-item-name',
  templateUrl: './item-name.component.html'
})

export class ItemNameComponent {
  @Input() code: string;
  @Input() quality: number;
}
