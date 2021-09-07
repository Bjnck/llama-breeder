import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {HomeModule} from './home/home.module';
import {AppRoutingModule} from './app-routing.module';
import {CaptureModule} from './capture/capture.module';
import {Restangular, RestangularModule} from 'ngx-restangular';
import {REST_FULL_RESPONSE, RestangularConfigFactory, RestFullResponseFactory} from './restangular.custom';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {LoginModule} from './login/login.module';
import {SharedModule} from './shared/shared.module';
import {AccountModule} from './account/account.module';
import {ShopModule} from './shop/shop.module';
import {InventoryModule} from './inventory/inventory.module';
import {BarnModule} from './barn/barn.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    SharedModule,
    LoginModule,
    HomeModule,
    AccountModule,
    ShopModule,
    InventoryModule,
    CaptureModule,
    BarnModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    RouterModule.forRoot([
      {path: '', component: HomeComponent, pathMatch: 'full'}], {onSameUrlNavigation: 'reload'}),
    RestangularModule.forRoot(RestangularConfigFactory),
    BrowserAnimationsModule,
    MatProgressBarModule
  ],
  providers: [
    {provide: REST_FULL_RESPONSE, useFactory: RestFullResponseFactory, deps: [Restangular]}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
