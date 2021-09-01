import {NgModule} from '@angular/core';

import {BrowserModule} from "@angular/platform-browser";
import {HeaderComponent} from "./header/header.component";
import {UserResolve} from "./user/user.resolve";
import {UserService} from "./user/user.service";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {RouterModule} from "@angular/router";
import {AuthService} from "./auth/auth.service";
import {HeaderService} from "./header/header.service";
import {ItemService} from "./item/item.service";

@NgModule({
  declarations: [
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    RouterModule
  ],
  exports: [
    HeaderComponent
  ],
  providers: [
    HeaderService,
    UserResolve,
    UserService,
    ItemService,
    AuthService
  ]
})
export class SharedModule {
}
