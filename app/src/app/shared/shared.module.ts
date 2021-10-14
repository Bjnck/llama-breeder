import {NgModule} from '@angular/core';

import {BrowserModule} from '@angular/platform-browser';
import {HeaderComponent} from './header/header.component';
import {UserResolve} from './user/user.resolve';
import {UserService} from './user/user.service';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {RouterModule} from '@angular/router';
import {AuthService} from './auth/auth.service';
import {HeaderService} from './header/header.service';
import {ItemService} from './item/item.service';
import {ItemCountResolve} from './item/item-count.resolve';
import {ItemNameComponent} from './item/item-name.component';
import {ItemIconComponent} from './item/item-icon.component';
import {CreatureService} from './creature/creature.service';
import {LlamaComponent} from './creature/llama.component';
import {CreatureDetailsDialogComponent} from './creature/details/creature-details.dialog';
import {MatDialogModule} from '@angular/material/dialog';
import {FormsModule} from '@angular/forms';
import {CreatureGeneComponent} from './creature/creature-gene.component';
import {MatMenuModule} from '@angular/material/menu';
import {RomanPipe} from "./creature/details/roman.pipe";
import {MatDividerModule} from "@angular/material/divider";
import {RedeemCreatureDialogComponent} from "./creature/redeem/redeem-creature.dialog";

@NgModule({
  declarations: [
    HeaderComponent,
    ItemNameComponent,
    ItemIconComponent,
    LlamaComponent,
    CreatureDetailsDialogComponent,
    RedeemCreatureDialogComponent,
    CreatureGeneComponent,
    RomanPipe
  ],
  imports: [
    BrowserModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    RouterModule,
    MatDialogModule,
    FormsModule,
    MatMenuModule,
    MatDividerModule
  ],
  exports: [
    HeaderComponent,
    ItemNameComponent,
    ItemIconComponent,
    LlamaComponent,
    CreatureDetailsDialogComponent,
    CreatureGeneComponent
  ],
  providers: [
    HeaderService,
    UserResolve,
    UserService,
    ItemService,
    ItemCountResolve,
    CreatureService,
    AuthService
  ]
})
export class SharedModule {
}
