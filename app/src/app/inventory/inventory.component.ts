import {Component, OnInit} from '@angular/core';
import {User} from '../shared/user/user.interface';
import {ActivatedRoute} from '@angular/router';
import {HeaderService} from '../shared/header/header.service';
import {UserService} from '../shared/user/user.service';
import {Item} from '../shared/item/item.interface';
import {ItemService} from '../shared/item/item.service';
import {Pen} from '../pen/pen.interface';
import {PenService} from '../pen/pen.service';

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
  pen: Pen;
  itemsInPen: string[];

  filter: string;

  throttle = 0;
  distance = 2;
  page = 0;
  size = 20;

  constructor(private headerService: HeaderService,
              private userService: UserService,
              private itemService: ItemService,
              private penService: PenService,
              private route: ActivatedRoute) {
    this.headerService.showHeader('Inventory', false);
  }

  ngOnInit() {
    this.user = this.route.snapshot.data.user;
    this.items = this.route.snapshot.data.items;
    this.itemCount = this.route.snapshot.data.itemCount.totalElements;
    this.pen = this.route.snapshot.data.pens[0];
    this.itemsInPen = this.pen.items.map(item => item.id.toString());
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

  addToPen(item: Item) {
    this.pen.items.push({id: item.id});
    this.itemsInPen.push(item.id);
    this.penService.update(this.pen).subscribe(
      pen => {
      },
      error => {
        this.removeItem(item);
      });
  }

  removeFromPen(item: Item) {
    this.removeItem(item);
    this.penService.update(this.pen).subscribe(
      pen => {
      },
      error => {
        this.pen.items.push({id: item.id});
        this.itemsInPen.push(item.id);
      });
  }

  private removeItem(item: Item) {
    this.pen.items.forEach((i, index) => {
      if (i.id === item.id) {
        this.pen.items.splice(index, 1);
      }
    });

    this.itemsInPen.splice(this.itemsInPen.indexOf(item.id), 1);
  }
}
