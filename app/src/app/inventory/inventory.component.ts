import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HeaderService} from '../shared/header/header.service';
import {Item} from '../shared/item/item.interface';
import {ItemService} from '../shared/item/item.service';
import {Pen} from '../pen/pen.interface';
import {environment} from '../../environments/environment';
import {ItemCacheService} from '../shared/item/item-cache.service';
import {MatDialog} from '@angular/material/dialog';
import {ItemSearch} from '../shared/item/item-search.interface';
import {ItemDetailsDialogComponent} from './details/item-details.dialog';
import {ItemDetailsResponse} from './details/item-details-response.interface';

@Component({
  templateUrl: './inventory.component.html',
  styleUrls: [
    './inventory.component.sass',
    '../shared/shared-style.sass'
  ]
})
export class InventoryComponent implements OnInit {
  itemsMax = environment.itemsMax;
  itemLifeMax = environment.itemLifeMax;

  items: Item[];
  totalCount: number;
  filterCount: number;
  pens: Pen[];
  itemsInPen: string[] = [];

  // filters
  type: string;
  inPen: boolean;
  old: boolean;

  throttle = 0;
  distance = 2;
  page = 0;
  size = 20;
  loading = false;

  constructor(private headerService: HeaderService,
              private itemService: ItemService,
              private dialog: MatDialog,
              private route: ActivatedRoute) {
    this.headerService.showHeader('Inventory', false);
  }

  ngOnInit() {
    this.items = this.route.snapshot.data.items;
    this.pens = this.route.snapshot.data.pens;

    this.totalCount = ItemCacheService.getTotalElements();
    this.filterCount = ItemCacheService.getFilterElements();

    this.pens = this.route.snapshot.data.pens;
    this.pens.sort((a, b) => a.id.toString().localeCompare(b.id));
    this.pens.forEach(pen => pen.items.forEach(item => this.itemsInPen.push(item.id.toString())));
  }

  toggleTypeFilter(type: string) {
    this.type = type;
    this.resetList();
  }

  toggleInPenFilter(inPen: boolean) {
    this.inPen = inPen;
    this.resetList();
  }

  toggleOldFilter(old: boolean) {
    this.old = old;
    this.resetList();
  }

  resetList() {
    this.page = 0;
    this.itemService.list(this.size, this.page, true, this.getSearchParams())
      .subscribe((items: Item[]) => {
        this.items = items;
        this.filterCount = ItemCacheService.getFilterElements();
      });
  }

  onScroll() {
    if (this.items.length === (this.page + 1) * this.size) {
      this.loading = true;
    }
    this.itemService.list(this.size, ++this.page, false, this.getSearchParams())
      .subscribe((items: Item[]) => {
        if (items.length < this.size) {
          this.loading = false;
        }
        this.items.push(...items);
      });
  }

  private getSearchParams(): ItemSearch {
    const search: ItemSearch = {};
    if (this.type) {
      search.code = this.type;
    }
    if (this.inPen) {
      search.inPen = true;
    }
    if (this.old) {
      search.maxLife = '0';
    }
    return search;
  }

  openDetails(item: Item) {
    this.dialog.open(ItemDetailsDialogComponent, {
      data: {item, pens: this.pens, itemsIdInPen: this.itemsInPen},
      disableClose: true,
      position: {top: '50px'},
      width: '100%',
      maxWidth: '500px',
      minWidth: '340px',
      restoreFocus: false
    }).afterClosed().subscribe({
      next: (response: ItemDetailsResponse) => {
        if (response.deleted) {
          this.totalCount--;
          this.deleteFromList(response.itemId);
        }
        if (response.removeFromPen && this.inPen) {
          this.deleteFromList(response.itemId);
        }
      }
    });
  }

  private deleteFromList(id: string) {
    this.filterCount--;
    this.items.splice(this.items.findIndex(i => i.id.toString() === id.toString()), 1);
    this.itemService.list(1, this.items.length, false, this.getSearchParams())
      .subscribe((items: Item[]) => {
        if (items && items.length > 0 && !this.items.find(
          (i: Item) => i.id.toString() === items[0].id.toString())) {
          this.items.push(...items);
        }
      });
  }
}
