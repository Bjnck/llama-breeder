import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {HomeModule} from "./home/home.module";
import {AppRoutingModule} from "./app-routing.module";
import {CaptureModule} from "./capture/capture.module";
import {Restangular, RestangularModule} from "ngx-restangular";
import {REST_FULL_RESPONSE, RestangularConfigFactory, RestFullResponseFactory} from "./restangular.custom";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    HomeModule,
    CaptureModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    RouterModule.forRoot([
      {path: '', component: HomeComponent, pathMatch: 'full'}], {onSameUrlNavigation: 'reload'}),
    RestangularModule.forRoot(RestangularConfigFactory),
  ],
  providers: [
    {provide: REST_FULL_RESPONSE, useFactory: RestFullResponseFactory, deps: [Restangular]}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
