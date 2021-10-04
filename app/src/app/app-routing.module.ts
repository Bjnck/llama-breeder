import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {CaptureComponent} from './capture/capture.component';
import {UserResolve} from './shared/user/user.resolve';
import {CaptureResolve} from './capture/capture.resolve';
import {LoginComponent} from './login/login.component';
import {AuthGuard} from './shared/auth/auth.guard';
import {LoginGuard} from './login/login.guard';
import {AccountComponent} from './account/account.component';
import {ShopComponent} from './shop/shop.component';
import {ShopItemResolve} from './shop/item/shop-item.resolve';
import {InventoryComponent} from './inventory/inventory.component';
import {ItemListResolve} from './inventory/item-list.resolve';
import {ItemCountResolve} from './shared/item/item-count.resolve';
import {NetCountResolve} from './capture/launch/net-count.resolve';
import {BarnComponent} from './barn/barn.component';
import {CreatureListResolve} from './barn/creature-list.resolve';
import {CreatureCountResolve} from './barn/creature-count.resolve';
import {PenComponent} from './pen/pen.component';
import {PenListResolve} from "./pen/pen-list.resolve";
import {PenListContentResolve} from "./pen/pen-list-content.resolve";


const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    resolve: {
      user: UserResolve
    },
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [LoginGuard]
  },
  {
    path: 'account',
    component: AccountComponent,
    resolve: {
      user: UserResolve
    }
  },
  {
    path: 'shop',
    component: ShopComponent,
    resolve: {
      user: UserResolve,
      shopItems: ShopItemResolve,
      itemCount: ItemCountResolve
    },
    canActivate: [AuthGuard]
  },
  {
    path: 'inventory',
    component: InventoryComponent,
    resolve: {
      user: UserResolve,
      items: ItemListResolve,
      itemCount: ItemCountResolve,
      pens: PenListResolve
    },
    canActivate: [AuthGuard]
  },
  {
    path: 'capture',
    component: CaptureComponent,
    resolve: {
      user: UserResolve,
      captures: CaptureResolve,
      netCount: NetCountResolve
    },
    canActivate: [AuthGuard]
  },
  {
    path: 'barn',
    component: BarnComponent,
    resolve: {
      user: UserResolve,
      creatures: CreatureListResolve,
      creatureCount: CreatureCountResolve,
      pens: PenListResolve
    },
    canActivate: [AuthGuard]
  },
  {
    path: 'pen',
    component: PenComponent,
    resolve: {
      user: UserResolve,
      pens: PenListContentResolve
    },
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
