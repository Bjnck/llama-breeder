import {Component, OnInit} from '@angular/core';
import {User} from '../shared/user/user.interface';
import {ActivatedRoute} from '@angular/router';
import {HeaderService} from '../shared/header/header.service';
import {UserService} from '../shared/user/user.service';
import {Item} from '../shared/item/item.interface';
import {ItemService} from '../shared/item/item.service';
import {ItemDeleteDialogComponent} from './item-delete.dialog';
import {MatDialog} from '@angular/material/dialog';

@Component({
  templateUrl: './inventory.component.html',
  styleUrls: [
    './inventory.component.sass',
    '../shared/filter/filter.sass',
    '../shared/shared-style.sass',
    '../shared/item/item-display.sass'
  ]
})
export class InventoryComponent implements OnInit {
  user: User;
  items: Item[];
  itemCount: number;

  filter: string;

  throttle = 0;
  distance = 2;
  page = 0;
  size = 20;

  constructor(private headerService: HeaderService,
              private userService: UserService,
              private itemService: ItemService,
              private route: ActivatedRoute,
              private dialog: MatDialog) {
    this.headerService.showHeader('Inventory', false);
  }

  ngOnInit() {
    this.user = this.route.snapshot.data.user;
    this.items = this.route.snapshot.data.items;
    this.itemCount = this.route.snapshot.data.itemCount.totalElements;
  }

  toggleFilter(value: string) {
    this.filter = value;
    this.resetList();
  }

  onScroll() {
    this.itemService.list(this.size, ++this.page, this.filter)
      .subscribe((items: Item[]) => {
        this.items.push(...items);
      });
  }

  resetList() {
    this.page = 0;
    this.itemService.list(this.size, this.page, this.filter)
      .subscribe((items: Item[]) => {
        this.items = items;
      });
  }

  onDelete(item: Item) {
    this.dialog.open(ItemDeleteDialogComponent, {
      data: item,
      restoreFocus: false
    }).afterClosed().subscribe(result => {
      if (result) {
        this.delete(result);
      }
    });
  }

  delete(item: Item) {
    this.itemService.delete(item).subscribe((resp: any) => {
      this.itemCount--;
      this.items.splice(this.items.findIndex(i => i.id === item.id), 1);
      this.itemService.list(1, this.items.length, this.filter)
        .subscribe((items: Item[]) => {
          if (items && items.length > 0 && !this.items.find((i: Item) => i.id === items[0].id)) {
            this.items.push(...items);
          }
        });
    });
  }
}
