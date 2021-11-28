import {NgModule} from '@angular/core';

import {MatButtonModule} from '@angular/material/button';
import {LoginComponent} from './login.component';
import {SharedModule} from '../shared/shared.module';

@NgModule({
  declarations: [
    LoginComponent
  ],
    imports: [
        SharedModule,
        MatButtonModule
    ],
  exports: [],
  providers: []
})
export class LoginModule {
}
