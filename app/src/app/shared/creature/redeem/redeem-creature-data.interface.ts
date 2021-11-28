import {Creature} from '../creature.interface';
import {User} from '../../user/user.interface';

export interface RedeemCreatureData {
  user: User;
  creature: Creature;
}


