import {NgModule} from '@angular/core';

import {SharedModule} from '../shared/shared.module';
import {InventoryComponent} from './inventory.component';
import {ItemListResolve} from './item-list.resolve';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import {CommonModule} from '@angular/common';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatMenuModule} from '@angular/material/menu';

@NgModule({
  declarations: [
    InventoryComponent
  ],
  imports: [
    SharedModule,
    InfiniteScrollModule,
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule
  ],
  exports: [
  ],
  providers: [
    ItemListResolve
  ]
})
export class InventoryModule {
}
