import {Pen} from '../../../pen/pen.interface';
import {Creature, CreatureInfo} from '../creature.interface';
import {User} from '../../user/user.interface';

export interface CreatureDetailsData {
  user?: User;
  creature: Creature;
  pens: Pen[];
  creaturesIdInPen: string[];
  creaturesInPen?: Creature[];
  prices: CreatureInfo[];
}


