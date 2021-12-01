import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Item} from '../../shared/item/item.interface';
import {Pen} from '../../pen/pen.interface';
import {ItemDetailsData} from './item-details-data.interface';
import {ItemService} from '../../shared/item/item.service';
import {PenService} from '../../pen/pen.service';
import {ItemDetailsResponse} from './item-details-response.interface';

@Component({
  templateUrl: './item-details.dialog.html',
  styleUrls: [
    './item-details.dialog.sass'
  ]
})
export class ItemDetailsDialogComponent {
  itemLifeMax = environment.itemLifeMax;

  item: Item;
  pens: Pen[];
  itemsIdInPen: string[];

  constructor(@Inject(MAT_DIALOG_DATA) public data: ItemDetailsData,
              private dialog: MatDialog,
              private dialogRef: MatDialogRef<ItemDetailsDialogComponent>,
              private itemService: ItemService,
              private penService: PenService) {
    this.item = data.item;
    this.pens = data.pens;
    this.itemsIdInPen = data.itemsIdInPen;
  }

  addToPen(item: Item, pen: Pen) {
    this.addItem(item, pen);
    this.penService.update(pen).subscribe(
      next => this.onClose(),
      error => this.removeItem(item, pen));
  }

  addItem(item: Item, pen: Pen) {
    pen.items.push({id: item.id});
    this.itemsIdInPen.push(item.id);
  }

  removeFromPen(item: Item) {
    const pen = this.getPen(item);
    this.sendRemoveFromPen(item, pen).subscribe(
      next => this.onClose(false, true),
      error => this.addItem(item, pen)
    );
  }

  private sendRemoveFromPen(item: Item, pen: Pen): Observable<any> {
    this.removeItem(item, pen);
    return this.penService.update(pen);
  }

  onDelete(item: Item) {
    const pen = this.getPen(item);
    if (pen) {
      this.sendRemoveFromPen(item, pen).subscribe(
        next => this.delete(item),
        error => this.addItem(item, pen)
      );
    } else {
      this.delete(item);
    }
  }

  private removeItem(item: Item, pen: Pen) {
    pen.items.splice(pen.items.indexOf(pen.items.find(c => c.id === item.id)), 1);
    this.itemsIdInPen.splice(this.itemsIdInPen.indexOf(item.id), 1);
  }

  private delete(item: Item) {
    this.itemService.delete(item).subscribe({
      next: resp => this.onClose(true)
    });
  }

  private getPen(item: Item): Pen {
    const pens = this.pens.filter(pen => pen.items.filter(c => c.id.toString() === item.id.toString()).length > 0);
    if (pens.length > 0) {
      return pens[0];
    } else {
      return null;
    }
  }

  onClose(isDelete = false, isRemoveFromPen = false) {
    const response: ItemDetailsResponse = {
      itemId: this.item.id,
      deleted: false,
      removeFromPen: false
    };
    if (isDelete) {
      response.deleted = true;
    }
    if (isRemoveFromPen) {
      response.removeFromPen = true;
    }
    this.dialogRef.close(response);
  }
}
