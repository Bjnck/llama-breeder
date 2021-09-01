import {NgModule} from '@angular/core';

import {SharedModule} from "../shared/shared.module";
import {ShopComponent} from "./shop.component";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {ShopItemService} from "./item/shop-item.service";
import {ShopItemResolve} from "./item/shop-item.resolve";
import {ShopCategoryPipe} from "./category/shop-category.pipe";
import {CommonModule} from "@angular/common";
import {ShopCategoryComponent} from "./category/shop-category.component";
import {CountItemResolve} from "./count-item.resolve";

@NgModule({
  declarations: [
    ShopComponent,
    ShopCategoryComponent,
    ShopCategoryPipe
  ],
  imports: [
    SharedModule,
    MatButtonModule,
    MatIconModule,
    CommonModule
  ],
  exports:[
  ],
  providers: [
    ShopItemService,
    ShopItemResolve,
    CountItemResolve
  ]
})
export class ShopModule {
}
