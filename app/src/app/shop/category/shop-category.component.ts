import {Component, EventEmitter, Input, OnDestroy, Output} from '@angular/core';
import {User} from '../../shared/user/user.interface';
import {ShopItem} from '../item/shop-item.interface';
import {ItemService} from '../../shared/item/item.service';
import {Subscription} from 'rxjs';
import {UserService} from '../../shared/user/user.service';

@Component({
  selector: 'app-shop-category',
  templateUrl: './shop-category.component.html',
  styleUrls: ['./shop-category.component.sass',
    '../../shared/item/item-display.sass']
})

export class ShopCategoryComponent implements OnDestroy {
  @Input() shopItems: ShopItem[];
  @Input() user: User;
  @Input() categoryName: string;
  @Input() categoryCode: string;
  @Input() bestOnly: boolean;
  @Input() itemCount: number;

  @Output() countItemChangeEventEmitter: EventEmitter<number> = new EventEmitter<number>();

  purchaseSubscription: Subscription;

  constructor(private itemService: ItemService,
              private userService: UserService) {
  }

  ngOnDestroy() {
    if (this.purchaseSubscription) {
      this.purchaseSubscription.unsubscribe();
    }
  }

  purchase(shopItem: ShopItem) {
    this.purchaseSubscription = this.itemService.add(shopItem.code, shopItem.quality)
      .subscribe(
        (resp: any) => {
          this.itemCount++;
          this.countItemChangeEventEmitter.emit(this.itemCount);
          this.userService.updateCoins(this.user.coins - shopItem.coins);
        },
        err => {
          console.log(err);
        });
  }
}
