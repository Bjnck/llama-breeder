import {NgModule} from '@angular/core';

import {SharedModule} from '../shared/shared.module';
import {InventoryComponent} from './inventory.component';
import {ItemListResolve} from './item-list.resolve';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import {CommonModule} from '@angular/common';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {ItemDeleteDialogComponent} from './item-delete.dialog';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
  declarations: [
    InventoryComponent,
    ItemDeleteDialogComponent
  ],
  imports: [
    SharedModule,
    InfiniteScrollModule,
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatDialogModule
  ],
  exports: [
  ],
  providers: [
    ItemListResolve
  ]
})
export class InventoryModule {
}
