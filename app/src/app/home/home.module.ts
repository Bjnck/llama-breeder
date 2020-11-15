import {NgModule} from '@angular/core';

import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home.component";
import {LoginService} from "./login/login.service";
import {BrowserModule} from "@angular/platform-browser";
import {CommonModule} from "../common/common.module";

@NgModule({
  declarations: [
    LoginComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    CommonModule
  ],
  exports:[
  ],
  providers: [
    LoginService
  ]
})
export class HomeModule {
}
