import {NgModule} from '@angular/core';

import {HomeComponent} from "./home.component";
import {MatButtonModule} from "@angular/material/button";
import {SharedModule} from "../shared/shared.module";
import {RouterModule} from "@angular/router";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatIconModule} from "@angular/material/icon";

@NgModule({
  declarations: [
    HomeComponent
  ],
    imports: [
        SharedModule,
        MatButtonModule,
        RouterModule,
        MatGridListModule,
        MatIconModule
    ],
  exports:[
  ],
  providers: []
})
export class HomeModule {
}
