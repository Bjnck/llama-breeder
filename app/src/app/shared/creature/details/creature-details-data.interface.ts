import {Pen} from '../../../pen/pen.interface';
import {Creature} from '../creature.interface';
import {User} from '../../user/user.interface';

export interface CreatureDetailsData {
  user?: User;
  creature: Creature;
  pen: Pen;
  creaturesIdInPen: string[];
  creaturesInPen?: Creature[];
}


