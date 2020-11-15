import {NgModule} from '@angular/core';

import {BrowserModule} from "@angular/platform-browser";
import {HeaderComponent} from "./header/header.component";
import {UserResolve} from "./user/user.resolve";
import {UserService} from "./user/user.service";

@NgModule({
  declarations: [
    HeaderComponent
  ],
  imports: [
    BrowserModule
  ],
  exports: [
    HeaderComponent
  ],
  providers: [
    UserResolve,
    UserService
  ]
})
export class CommonModule {
}
