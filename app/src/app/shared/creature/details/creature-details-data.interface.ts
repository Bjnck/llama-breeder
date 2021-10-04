import {Pen} from '../../../pen/pen.interface';
import {Creature} from '../creature.interface';

export interface CreatureDetailsData {
  creature: Creature;
  pen: Pen;
  creaturesIdInPen: string[];
  creaturesInPen?: Creature[];
  closeOnRemove?: boolean;
}


