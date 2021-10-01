import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {Item} from '../shared/item/item.interface';

@Component({
  templateUrl: './item-delete.dialog.html',
  styleUrls: [
    './item-delete.dialog.sass'
  ]
})
export class ItemDeleteDialogComponent {
  item: Item;

  constructor(@Inject(MAT_DIALOG_DATA) public data: Item) {
    this.item = data;
  }
}
