import {Component} from "@angular/core";
import {User} from "../shared/user/user.interface";
import {ActivatedRoute} from "@angular/router";
import {HeaderService} from "../shared/header/header.service";
import {UserService} from "../shared/user/user.service";
import {Item} from "../shared/item/item.interface";

@Component({
  selector: 'inventory',
  templateUrl: './inventory.component.html',
  styleUrls: [
    './inventory.component.sass',
    '../shared/shared-style.sass'
  ]
})

export class InventoryComponent {
  user: User;
  items: Item[];

  constructor(private headerService: HeaderService,
              private userService: UserService,
              private route: ActivatedRoute) {
    this.headerService.showHeader("Inventory", false);
  }

  ngOnInit() {
    this.user = this.route.snapshot.data.user;
    this.items = this.route.snapshot.data.items;
    console.log(this.items);
  }
}
