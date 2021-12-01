import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-item-category-name',
  templateUrl: './item-category-name.component.html'
})

export class ItemCategoryNameComponent {
  @Input() code: string;
}
