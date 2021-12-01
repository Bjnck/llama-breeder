import {NgModule} from '@angular/core';

import {SharedModule} from '../shared/shared.module';
import {ShopComponent} from './shop.component';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {ShopItemService} from './item/shop-item.service';
import {ShopItemResolve} from './item/shop-item.resolve';
import {ShopCategoryPipe} from './category/shop-category.pipe';
import {CommonModule} from '@angular/common';
import {ShopCategoryComponent} from './category/shop-category.component';
import {MatRippleModule} from '@angular/material/core';
import {ShopValidationDialogComponent} from './validation/shop-validation.dialog';
import {MatDialogModule} from "@angular/material/dialog";
import {MatMenuModule} from "@angular/material/menu";

@NgModule({
  declarations: [
    ShopComponent,
    ShopCategoryComponent,
    ShopCategoryPipe,
    ShopValidationDialogComponent
  ],
  imports: [
    SharedModule,
    MatButtonModule,
    MatIconModule,
    CommonModule,
    MatRippleModule,
    MatDialogModule,
    MatMenuModule
  ],
  exports: [],
  providers: [
    ShopItemService,
    ShopItemResolve
  ]
})
export class ShopModule {
}
