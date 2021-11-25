import {Pipe, PipeTransform} from '@angular/core';
import {CreatureInfo} from '../creature.interface';

@Pipe({name: 'creaturePrice'})
export class CreaturePricesPipe implements PipeTransform {

  transform(prices: CreatureInfo[], generation: number): number {
    return prices.find(price => price.generation === generation).price;
  }
}
