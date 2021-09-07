import {Capture} from './capture.interface';
import {Creature} from '../shared/creature/creature.interface';

export interface CaptureCreature {
  capture: Capture;
  creature?: Creature;
}
