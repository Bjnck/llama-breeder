import {NgModule} from '@angular/core';

import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {FormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {SharedModule} from "../shared/shared.module";
import {AccountComponent} from "./account.component";

@NgModule({
  declarations: [
    AccountComponent
  ],
  imports: [
    SharedModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    BrowserModule,
    MatProgressBarModule
  ],
  exports: [],
  providers: [
  ]
})
export class AccountModule {
}
