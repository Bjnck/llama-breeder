import {Creature} from '../shared/creature/creature.interface';
import {Item} from '../shared/item/item.interface';
import {Pen} from './pen.interface';

export interface PenWithContent {
  pen: Pen;
  creatures: Creature[];
  items: Item[];
}
