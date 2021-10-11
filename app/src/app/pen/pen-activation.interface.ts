import {Item} from '../shared/item/item.interface';
import {Creature} from '../shared/creature/creature.interface';

export interface PenActivation {
  item: Item;
  creatures: Creature[];
}
