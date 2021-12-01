import {Component, OnInit} from '@angular/core';
import {User} from '../shared/user/user.interface';
import {ActivatedRoute} from '@angular/router';
import {HeaderService} from '../shared/header/header.service';
import {UserService} from '../shared/user/user.service';
import {ShopItem} from './item/shop-item.interface';

@Component({
  templateUrl: './shop.component.html',
  styleUrls: [
    './shop.component.sass',
    '../shared/filter/filter.sass',
    '../shared/shared-style.sass'
  ]
})

export class ShopComponent implements OnInit {
  user: User;
  shopItems: ShopItem[];

  constructor(private headerService: HeaderService,
              private userService: UserService,
              private route: ActivatedRoute) {
    this.headerService.showHeader('Shop', false);
  }

  ngOnInit() {
    this.user = this.route.snapshot.data.user;
    this.shopItems = this.route.snapshot.data.shopItems;
  }
}
