package hrpg.server.creature.service;

import hrpg.server.creature.dao.Creature;
import hrpg.server.item.type.ItemCode;

import java.util.Set;

import static hrpg.server.creature.type.CreatureConstant.*;

public class CreatureUtil {

    public static boolean isBreedable(Creature creature) {
        return creature.getDetails().getMaturity() >= MATURITY_MAX &&
                creature.getDetails().getLove() >= STATS_MAX &&
                creature.getDetails().getBreedingCount() < BREEDING_MAX &&
                !creature.getDetails().isPregnant();
    }

    public static boolean isHittable(Creature creature, ItemCode itemCode) {
        if (creature.getDetails().getEnergy() <= ENERGY_MIN) return false;
        if (creature.getDetails().getMaturity() < MATURITY_MAX) return false;
        if (creature.getDetails().isPregnant()) return false;

        switch (itemCode) {
            case HUNGER:
                if (creature.getDetails().getHunger() >= STATS_MAX) return false;
                break;
            case THIRST:
                if (creature.getDetails().getThirst() >= STATS_MAX) return false;
                break;
            case LOVE:
                if (creature.getDetails().getLove() >= STATS_MAX ||
                        creature.getDetails().getThirst() < STATS_LOVE_REQUIREMENT ||
                        creature.getDetails().getHunger() < STATS_LOVE_REQUIREMENT) return false;
                break;
        }

        return true;
    }

    public static boolean isHittable(Creature creature, Set<ItemCode> itemCodes) {
        return itemCodes.stream().anyMatch(itemCode -> isHittable(creature, itemCode));
    }
}
