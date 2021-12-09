import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-item-title',
  templateUrl: './item-title.component.html',
  styleUrls: ['./item-title.component.sass']
})

export class ItemTitleComponent {
  @Input() name: string;
  @Input() code: string;
  @Input() quality: number;
}
