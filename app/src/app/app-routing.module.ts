import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {CaptureComponent} from "./capture/capture.component";
import {UserResolve} from "./common/user/user.resolve";
import {CaptureResolve} from "./capture/capture.resolve";


const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    resolve: {
      user: UserResolve
    }
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
