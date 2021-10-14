import {NgModule} from '@angular/core';

import {BrowserModule} from '@angular/platform-browser';
import {CaptureComponent} from './capture.component';
import {CaptureResolve} from './capture.resolve';
import {CaptureService} from './capture.service';
import {SharedModule} from '../shared/shared.module';
import {LaunchCaptureComponent} from './launch/launch-capture.component';
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {HistoryCaptureComponent} from './history/history-capture.component';
import {NetCountResolve} from './launch/net-count.resolve';
import {RedeemCaptureDialogComponent} from './launch/redeem/redeem-capture.dialog';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
  declarations: [
    CaptureComponent,
    HistoryCaptureComponent,
    LaunchCaptureComponent,
    RedeemCaptureDialogComponent
  ],
  imports: [
    BrowserModule,
    SharedModule,
    MatMenuModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule
  ],
  exports: [],
  providers: [
    CaptureResolve,
    CaptureService,
    NetCountResolve
  ]
})
export class CaptureModule {
}
