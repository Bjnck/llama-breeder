import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {HomeModule} from './home/home.module';
import {AppRoutingModule} from './app-routing.module';
import {CaptureModule} from './capture/capture.module';
import {Restangular, RestangularModule} from 'ngx-restangular';
import {REST_FULL_RESPONSE, RestangularConfigFactory, RestFullResponseFactory} from './shared/rest/restangular.custom';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {LoginModule} from './login/login.module';
import {SharedModule} from './shared/shared.module';
import {AccountModule} from './account/account.module';
import {ShopModule} from './shop/shop.module';
import {InventoryModule} from './inventory/inventory.module';
import {BarnModule} from './barn/barn.module';
import {PenModule} from './pen/pen.module';
import {AngularFireModule} from '@angular/fire';
import {environment} from '../environments/environment';
import {AngularFireAuthModule} from '@angular/fire/auth';
import {AngularFireAuthGuardModule} from '@angular/fire/auth-guard';
import {AuthService} from './shared/auth/auth.service';

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
    PenModule,

    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatProgressBarModule,

    AppRoutingModule,
    RouterModule.forRoot([], {onSameUrlNavigation: 'reload'}),

    RestangularModule.forRoot([AuthService], RestangularConfigFactory),

    AngularFireModule.initializeApp(environment.firebase),
    AngularFireAuthModule,
    AngularFireAuthGuardModule
  ],
  providers: [
    {provide: REST_FULL_RESPONSE, useFactory: RestFullResponseFactory, deps: [Restangular]}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
