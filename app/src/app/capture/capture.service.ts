import {Inject, Injectable} from '@angular/core';
import {forkJoin, Observable} from 'rxjs';
import {Restangular} from 'ngx-restangular';
import {Capture} from './capture.interface';
import {REST_FULL_RESPONSE} from '../restangular.custom';
import {flatMap, map, switchMap} from 'rxjs/operators';
import {CaptureCreature} from './capture-creature.interface';
import {Creature} from '../shared/creature/creature.interface';
import {CreatureService} from '../shared/creature/creature.service';

@Injectable()
export class CaptureService {

  baseRest = this.restangular.all('captures');

  constructor(@Inject(REST_FULL_RESPONSE) public restFullResponse,
              private restangular: Restangular,
              private creatureService: CreatureService) {
  }

  static getPair(capture: Capture, creature: Creature): CaptureCreature {
    return {capture, creature};
  }

  listCaptures(size: number): Observable<CaptureCreature[]> {
    return this.baseRest.getList({size})
      .pipe(
        switchMap((captures: Capture[]) => {
          if (captures.length <= 0) {
            return new Observable<CaptureCreature[]>(observer => {
              observer.next([]);
              observer.complete();
            });
          } else {
            return forkJoin(
              captures.map((capture: Capture) => {
                if (capture.creatureId) {
                  return this.creatureService.get(capture.creatureId)
                    .pipe(map((creature: Creature) => CaptureService.getPair(capture, creature)));
                } else {
                  return new Observable<CaptureCreature>(observer => {
                    observer.next(CaptureService.getPair(capture, null));
                    observer.complete();
                  });
                }
              })
            );
          }
        })
      );
  }

  createCapture(quality: number): Observable<Capture> {
    return this.restFullResponse.all('captures').post({quality})
      .pipe(flatMap((response: any) =>
        this.baseRest.oneUrl('captures', response.headers.headers.get('location')).get()));
  }
}
