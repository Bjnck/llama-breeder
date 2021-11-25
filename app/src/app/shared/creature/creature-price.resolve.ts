import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {CreatureInfo} from './creature.interface';
import {CreatureService} from './creature.service';

@Injectable()
export class CreaturePriceResolve implements Resolve<CreatureInfo[]> {

  constructor(private creatureService: CreatureService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<CreatureInfo[]> {
    return this.creatureService.getPrices();
  }
}
