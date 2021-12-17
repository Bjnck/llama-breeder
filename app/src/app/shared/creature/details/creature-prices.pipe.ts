import {Pipe, PipeTransform} from '@angular/core';
import {Creature, CreatureInfo} from '../creature.interface';

@Pipe({name: 'creaturePrice'})
export class CreaturePricesPipe implements PipeTransform {

  transform(prices: CreatureInfo[], creature: Creature): number {
    let price = prices.find(p => p.generation === creature.generation).price;

    if (creature.colors.color2 != null) {
      price += (price / 2);
      price = Math.floor(price);
    }

    if (creature.genes != null && ('CRESUS' === creature.genes.gene1 || 'CRESUS' === creature.genes.gene2)) {
      price *= 3;
    }
    return price;
  }
}
