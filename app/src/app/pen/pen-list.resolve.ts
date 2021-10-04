import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {PenService} from './pen.service';
import {Pen} from './pen.interface';

@Injectable()
export class PenListResolve implements Resolve<Pen[]> {

  constructor(private penService: PenService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Pen[]> {
    return this.penService.list();
  }
}
