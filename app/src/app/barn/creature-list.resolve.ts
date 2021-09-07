import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {Creature} from '../shared/creature/creature.interface';
import {CreatureService} from '../shared/creature/creature.service';

@Injectable()
export class CreatureListResolve implements Resolve<Creature[]> {

  constructor(private creatureService: CreatureService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Creature[]> {
    return this.creatureService.list(20, 0);
  }
}
