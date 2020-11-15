import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve} from "@angular/router";
import {Observable} from "rxjs";
import {Capture} from "./capture.interface";
import {CaptureService} from "./capture.service";
import {LoginService} from "../home/login/login.service";

@Injectable()
export class CaptureResolve implements Resolve<Capture[]> {

  constructor(private captureService: CaptureService,
              private loginService: LoginService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Capture[]> {
    if (this.loginService.checkCredentials())
      return this.captureService.listCaptures(11);
    else
      return null;
  }
}
