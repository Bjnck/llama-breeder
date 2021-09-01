import {Component, EventEmitter, Input, Output} from "@angular/core";
import {User} from "../../shared/user/user.interface";
import {ShopItem} from "../item/shop-item.interface";
import {ItemService} from "../../shared/item/item.service";
import {Subscription} from "rxjs";
import {UserService} from "../../shared/user/user.service";
import {Header} from "../../shared/header/header.interface";

@Component({
  selector: 'shop-category',
  templateUrl: './shop-category.component.html',
  styleUrls: ['./shop-category.component.sass']
})

export class ShopCategoryComponent {
  @Input() shopItems: ShopItem[];
  @Input() user: User;
  @Input() categoryName: string;
  @Input() categoryCode: string;
  @Input() bestOnly: boolean;
  @Input() countItem: number;

  @Output() countItemChangeEventEmitter: EventEmitter<number> = new EventEmitter<number>();

  purchaseSubscription: Subscription;

  constructor(private itemService: ItemService,
              private userService: UserService) {
  }

  ngOnDestroy() {
    if (this.purchaseSubscription)
      this.purchaseSubscription.unsubscribe();
  }

  purchase(shopItem: ShopItem) {
    this.purchaseSubscription = this.itemService.buy(shopItem.code, shopItem.quality)
      .subscribe(
        (user: any) => {
          this.countItem++;
          this.countItemChangeEventEmitter.emit(this.countItem);
          this.userService.updateCoins(this.user.coins - shopItem.coins);
        },
        err => {
          console.log("error");
        });
  }
}
