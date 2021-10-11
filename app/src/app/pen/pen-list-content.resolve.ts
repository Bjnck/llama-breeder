import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {PenService} from './pen.service';
import {PenWithContent} from './pen-with-content.interface';

@Injectable()
export class PenListContentResolve implements Resolve<PenWithContent[]> {

  constructor(private penService: PenService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<PenWithContent[]> {
    return this.penService.listWithContent();
  }
}
