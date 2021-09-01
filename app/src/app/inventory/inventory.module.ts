import {NgModule} from '@angular/core';

import {SharedModule} from "../shared/shared.module";
import {InventoryComponent} from "./inventory.component";
import {ItemListResolve} from "./item-list.resolve";

@NgModule({
  declarations: [
    InventoryComponent
  ],
  imports: [
    SharedModule
  ],
  exports:[
  ],
  providers: [
    ItemListResolve
  ]
})
export class InventoryModule {
}
