import {NgModule} from '@angular/core';

import {BrowserModule} from "@angular/platform-browser";
import {CaptureComponent} from "./capture.component";
import {CaptureHistoryComponent} from "./capture-history.component";
import {NewCaptureComponent} from "./new-capture.component";
import {CaptureResolve} from "./capture.resolve";
import {CaptureService} from "./capture.service";
import {SharedModule} from "../shared/shared.module";

@NgModule({
  declarations: [
    CaptureComponent,
    CaptureHistoryComponent,
    NewCaptureComponent
  ],
  imports: [
    BrowserModule,
    SharedModule
  ],
  exports: [
    CaptureComponent,
    CaptureHistoryComponent,
    NewCaptureComponent
  ],
  providers: [
    CaptureResolve,
    CaptureService
  ]
})
export class CaptureModule {
}
