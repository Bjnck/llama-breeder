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
import {ItemNameComponent} from './item/name/item-name.component';
import {ItemIconComponent} from './item/icon/item-icon.component';
import {CreatureService} from './creature/creature.service';
import {LlamaComponent} from './creature/llama.component';
import {CreatureDetailsDialogComponent} from './creature/details/creature-details.dialog';
import {MatDialogModule} from '@angular/material/dialog';
import {FormsModule} from '@angular/forms';
import {CreatureGeneComponent} from './creature/creature-gene.component';
import {MatMenuModule} from '@angular/material/menu';
import {RomanPipe} from './creature/details/roman.pipe';
import {MatDividerModule} from '@angular/material/divider';
import {RedeemCreatureDialogComponent} from './creature/redeem/redeem-creature.dialog';
import {UtcPipe} from './timer/utc.pipe';
import {RestService} from './rest/rest.service';
import {CreaturePriceResolve} from './creature/creature-price.resolve';
import {CreaturePricesPipe} from './creature/details/creature-prices.pipe';
import {ItemListResolve} from './item/item-list.resolve';
import {ItemTitleComponent} from './item/title/item-title.component';
import {ItemCategoryNameComponent} from './item/name/item-category-name.component';
import {ItemDescriptionComponent} from './item/description/item-description.component';

@NgModule({
  declarations: [
    HeaderComponent,
    ItemNameComponent,
    ItemIconComponent,
    ItemCategoryNameComponent,
    ItemTitleComponent,
    ItemDescriptionComponent,
    LlamaComponent,
    CreatureDetailsDialogComponent,
    RedeemCreatureDialogComponent,
    CreatureGeneComponent,
    CreaturePricesPipe,
    RomanPipe,
    UtcPipe
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
    CreatureGeneComponent,
    RomanPipe,
    UtcPipe,
    ItemTitleComponent,
    ItemCategoryNameComponent,
    ItemDescriptionComponent
  ],
  providers: [
    HeaderService,
    UserResolve,
    UserService,
    ItemService,
    ItemListResolve,
    CreatureService,
    CreaturePriceResolve,
    AuthService,
    RestService
  ]
})
export class SharedModule {
}
