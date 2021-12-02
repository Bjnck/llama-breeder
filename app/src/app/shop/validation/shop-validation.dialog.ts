import {Component, Inject} from '@angular/core';
import {User} from '../../shared/user/user.interface';
import {ShopItem} from '../item/shop-item.interface';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ItemService} from '../../shared/item/item.service';
import {ItemCacheService} from '../../shared/item/item-cache.service';
import {UserService} from '../../shared/user/user.service';
import {environment} from '../../../environments/environment';

@Component({
  templateUrl: './shop-validation.dialog.html',
  styleUrls: [
    './shop-validation.dialog.sass'
  ]
})
export class ShopValidationDialogComponent {
  itemsMax = environment.itemsMax;

  user: User;
  item: ShopItem;
  itemCount: number;

  constructor(@Inject(MAT_DIALOG_DATA) public data,
              private dialogRef: MatDialogRef<ShopValidationDialogComponent>,
              private itemService: ItemService,
              private userService: UserService) {
    this.user = data.user;
    this.item = data.item;
    this.itemCount = ItemCacheService.getTotalElements();
  }

  purchase() {
    this.itemService.add(this.item.code, this.item.quality)
      .subscribe({
        next: value => {
          this.userService.updateCoins(this.user.coins - this.item.coins);
          this.dialogRef.close(true);
        }
      });
  }
}
