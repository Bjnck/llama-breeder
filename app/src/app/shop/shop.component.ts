import {Component} from "@angular/core";
import {User} from "../shared/user/user.interface";
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../shared/auth/auth.service";
import {HeaderService} from "../shared/header/header.service";
import {UserService} from "../shared/user/user.service";
import {ShopItem} from "./item/shop-item.interface";

@Component({
  selector: 'shop',
  templateUrl: './shop.component.html',
  styleUrls: [
    './shop.component.sass',
    '../shared/shared-style.sass'
  ]
})

export class ShopComponent {
  user: User;
  shopItems: ShopItem[];
  countItem: number;

  bestOnly: boolean = false;

  constructor(private headerService: HeaderService,
              private userService: UserService,
              private route: ActivatedRoute) {
    this.headerService.showHeader("Shop", false);
  }

  ngOnInit() {
    this.user = this.route.snapshot.data.user;
    this.shopItems = this.route.snapshot.data.shopItems;
    this.countItem = this.route.snapshot.data.countItem.totalElements;
  }

  toggleBestButton() {
    this.bestOnly = !this.bestOnly;
  }

  updateCountItem(count: number){
    this.countItem = count;
  }
}
