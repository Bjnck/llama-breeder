import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {CaptureComponent} from './capture/capture.component';
import {UserResolve} from './shared/user/user.resolve';
import {CaptureResolve} from './capture/capture.resolve';
import {LoginComponent} from './login/login.component';
import {AccountComponent} from './account/account.component';
import {ShopComponent} from './shop/shop.component';
import {ShopItemResolve} from './shop/item/shop-item.resolve';
import {InventoryComponent} from './inventory/inventory.component';
import {ItemListResolve} from './shared/item/item-list.resolve';
import {NetCountResolve} from './capture/launch/net-count.resolve';
import {BarnComponent} from './barn/barn.component';
import {CreatureListResolve} from './barn/creature-list.resolve';
import {PenComponent} from './pen/pen.component';
import {PenListResolve} from './pen/pen-list.resolve';
import {PenListContentResolve} from './pen/pen-list-content.resolve';
import {canActivate, redirectLoggedInTo, redirectUnauthorizedTo} from '@angular/fire/auth-guard';
import {PenPriceResolve} from './pen/pen-price.resolve';
import {CreaturePriceResolve} from './shared/creature/creature-price.resolve';

const redirectUnauthorizedToLogin = () => redirectUnauthorizedTo(['login']);
const redirectLoggedInToHome = () => redirectLoggedInTo(['/']);

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    resolve: {
      user: UserResolve
    },
    ...canActivate(redirectUnauthorizedToLogin)
  },
  {
    path: 'login',
    component: LoginComponent,
    ...canActivate(redirectLoggedInToHome)
  },
  {
    path: 'account',
    component: AccountComponent,
    resolve: {
      user: UserResolve
    },
    ...canActivate(redirectUnauthorizedToLogin)
  },
  {
    path: 'shop',
    component: ShopComponent,
    resolve: {
      user: UserResolve,
      items: ItemListResolve,
      shopItems: ShopItemResolve
    },
    ...canActivate(redirectUnauthorizedToLogin)
  },
  {
    path: 'inventory',
    component: InventoryComponent,
    resolve: {
      user: UserResolve,
      items: ItemListResolve,
      pens: PenListResolve
    },
    ...canActivate(redirectUnauthorizedToLogin)
  },
  {
    path: 'capture',
    component: CaptureComponent,
    resolve: {
      user: UserResolve,
      captures: CaptureResolve,
      netCount: NetCountResolve
    },
    ...canActivate(redirectUnauthorizedToLogin)
  },
  {
    path: 'barn',
    component: BarnComponent,
    resolve: {
      user: UserResolve,
      creatures: CreatureListResolve,
      pens: PenListResolve,
      prices: CreaturePriceResolve
    },
    ...canActivate(redirectUnauthorizedToLogin)
  },
  {
    path: 'pen',
    component: PenComponent,
    resolve: {
      user: UserResolve,
      pens: PenListContentResolve,
      prices: PenPriceResolve,
      creaturePrices: CreaturePriceResolve
    },
    ...canActivate(redirectUnauthorizedToLogin)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
