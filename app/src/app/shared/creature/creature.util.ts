import {Creature} from './creature.interface';

export class CreatureUtil {
  public static updateStats(existingCreature: Creature, newValueCreature: Creature) {
    existingCreature.pregnant = newValueCreature.pregnant;
    existingCreature.pregnancyStartTime = newValueCreature.pregnancyStartTime;
    existingCreature.pregnancyEndTime = newValueCreature.pregnancyEndTime;
    existingCreature.pregnancyMale = newValueCreature.pregnancyMale;
    existingCreature.pregnancyCount = newValueCreature.pregnancyCount;
    existingCreature.statistics.energy = newValueCreature.statistics.energy;
    existingCreature.statistics.maturity = newValueCreature.statistics.maturity;
    existingCreature.statistics.thirst = newValueCreature.statistics.thirst;
    existingCreature.statistics.hunger = newValueCreature.statistics.hunger;
    existingCreature.statistics.love = newValueCreature.statistics.love;
  }
}
