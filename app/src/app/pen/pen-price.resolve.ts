import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {PenService} from './pen.service';
import {Pen, PenInfo} from './pen.interface';

@Injectable()
export class PenPriceResolve implements Resolve<PenInfo[]> {

  constructor(private penService: PenService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<PenInfo[]> {
    return this.penService.getPrices();
  }
}
