import {NgModule} from '@angular/core';

import {SharedModule} from '../shared/shared.module';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import {CommonModule} from '@angular/common';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {BarnComponent} from './barn.component';
import {CreatureListResolve} from './creature-list.resolve';
import {MatMenuModule} from '@angular/material/menu';
import {MatRippleModule} from '@angular/material/core';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";

@NgModule({
  declarations: [
    BarnComponent
  ],
  imports: [
    SharedModule,
    InfiniteScrollModule,
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatRippleModule,
    MatProgressSpinnerModule,
  ],
  exports: [],
  providers: [
    CreatureListResolve
  ]
})
export class BarnModule {
}
