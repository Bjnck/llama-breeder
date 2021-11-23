import {Creature} from '../creature.interface';

export interface CreatureDetailsResponse {
  creatureId: string;
  deleted: boolean;
  removeFromPen: boolean;
  baby?: Creature;
  creatures: Creature[];
}


