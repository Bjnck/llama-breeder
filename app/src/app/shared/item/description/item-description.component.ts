import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-item-description',
  templateUrl: './item-description.component.html',
  styleUrls: ['./item-description.component.sass']
})

export class ItemDescriptionComponent {
  @Input() code: string;
  @Input() quality: number;
}
