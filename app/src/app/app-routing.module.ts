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
      itemCount: ItemCountResolve
    },
    canActivate: [AuthGuard]
  },
  {
    path: 'capture',
    component: CaptureComponent,
    resolve: {
      user: UserResolve,
      captures: CaptureResolve
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
