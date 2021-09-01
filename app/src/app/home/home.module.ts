import {NgModule} from '@angular/core';

import {HomeComponent} from "./home.component";
import {MatButtonModule} from "@angular/material/button";
import {SharedModule} from "../shared/shared.module";
import {RouterModule} from "@angular/router";
import {MatGridListModule} from "@angular/material/grid-list";

@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    SharedModule,
    MatButtonModule,
    RouterModule,
    MatGridListModule
  ],
  exports:[
  ],
  providers: []
})
export class HomeModule {
}
