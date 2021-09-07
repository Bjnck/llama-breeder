import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {CaptureService} from './capture.service';
import {CaptureCreature} from './capture-creature.interface';
import {Observable} from 'rxjs';

@Injectable()
export class CaptureResolve implements Resolve<CaptureCreature[]> {

  constructor(private captureService: CaptureService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<CaptureCreature[]> {
    return this.captureService.listCaptures(11);
  }
}
