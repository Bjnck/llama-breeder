import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {CaptureService} from './capture.service';
import {Observable} from 'rxjs';
import {Capture} from './capture.interface';

@Injectable()
export class CaptureResolve implements Resolve<Capture[]> {

  constructor(private captureService: CaptureService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Capture[]> {
    return this.captureService.list(11);
  }
}
