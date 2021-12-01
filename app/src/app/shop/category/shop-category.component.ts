import {Component, Input} from '@angular/core';
import {User} from '../../shared/user/user.interface';
import {ShopItem} from '../item/shop-item.interface';
import {ItemService} from '../../shared/item/item.service';
import {UserService} from '../../shared/user/user.service';
import {MatDialog} from '@angular/material/dialog';
import {ShopValidationDialogComponent} from '../validation/shop-validation.dialog';

@Component({
  selector: 'app-shop-category',
  templateUrl: './shop-category.component.html',
  styleUrls: ['./shop-category.component.sass']
})

export class ShopCategoryComponent {
  @Input() shopItems: ShopItem[];
  @Input() user: User;
  @Input() categoryCode: string;

  constructor(private itemService: ItemService,
              private userService: UserService,
              private dialog: MatDialog) {
  }

  openValidation(item: ShopItem) {
    if (item.coins <= this.user.coins) {
      this.dialog.open(ShopValidationDialogComponent, {
        data: {user: this.user, item},
        disableClose: true,
        position: {top: '50px'},
        width: '100%',
        maxWidth: '500px',
        minWidth: '340px',
        restoreFocus: false
      });
    }
  }


}
