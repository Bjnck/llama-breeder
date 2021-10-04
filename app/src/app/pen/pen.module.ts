import {NgModule} from '@angular/core';

import {SharedModule} from '../shared/shared.module';
import {CommonModule} from '@angular/common';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {PenComponent} from './pen.component';
import {PenFieldComponent} from './field/pen-field.component';
import {PenItemsComponent} from './items/pen-items.component';
import {MatGridListModule} from '@angular/material/grid-list';
import {PenService} from './pen.service';
import {PenListResolve} from './pen-list.resolve';
import {PenListContentResolve} from './pen-list-content.resolve';
import {MatRippleModule} from "@angular/material/core";
import {PenFieldCreatureComponent} from "./field/pen-field-creature.component";

@NgModule({
  declarations: [
    PenComponent,
    PenFieldComponent,
    PenFieldCreatureComponent,
    PenItemsComponent
  ],
  imports: [
    SharedModule,
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatGridListModule,
    MatRippleModule
  ],
  exports: [
  ],
  providers: [
    PenService,
    PenListResolve,
    PenListContentResolve
  ]
})
export class PenModule {
}
