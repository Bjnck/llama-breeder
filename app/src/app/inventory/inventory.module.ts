import {NgModule} from '@angular/core';

import {SharedModule} from '../shared/shared.module';
import {InventoryComponent} from './inventory.component';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import {CommonModule} from '@angular/common';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatMenuModule} from '@angular/material/menu';
import {ItemDetailsDialogComponent} from './details/item-details.dialog';
import {MatRippleModule} from "@angular/material/core";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatDialogModule} from "@angular/material/dialog";

@NgModule({
  declarations: [
    InventoryComponent,
    ItemDetailsDialogComponent
  ],
  imports: [
    SharedModule,
    InfiniteScrollModule,
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatRippleModule,
    MatProgressSpinnerModule,
    MatDialogModule
  ],
  exports: [
  ],
  providers: [
  ]
})
export class InventoryModule {
}
