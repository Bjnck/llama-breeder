import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve} from "@angular/router";
import {Observable} from "rxjs";
import {Capture} from "./capture.interface";
import {CaptureService} from "./capture.service";
import {AuthService} from "../shared/auth/auth.service";

@Injectable()
export class CaptureResolve implements Resolve<Capture[]> {

  constructor(private captureService: CaptureService,
              private authService: AuthService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Capture[]> {
    if (this.authService.isLoggedIn())
      return this.captureService.listCaptures(11);
    else
      return null;
  }
}
