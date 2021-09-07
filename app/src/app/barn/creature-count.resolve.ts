import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {CreatureService} from '../shared/creature/creature.service';
import {Page} from '../shared/page/page.interface';

@Injectable()
export class CreatureCountResolve implements Resolve<Page> {

  constructor(private creatureService: CreatureService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Page> {
    return this.creatureService.count();
  }
}
