package hrpg.server.creature.service;

import hrpg.server.creature.dao.Creature;
import hrpg.server.item.type.ItemCode;

import java.util.Set;

public class CreatureUtil {

    public static boolean isBreedable(Creature creature) {
        return creature.getDetails().getMaturity() >= 1000 &&
                creature.getDetails().getLove() >= 100 &&
                creature.getDetails().getBreedingCount() < 10 &&
                !creature.getDetails().isPregnant();
    }

    public static boolean isHittable(Creature creature, ItemCode itemCode) {
        if (creature.getDetails().getEnergy() <= 0) return false;
        if (creature.getDetails().getMaturity() < 1000) return false;
        if (creature.getDetails().isPregnant()) return false;

        switch (itemCode) {
            case HUNGER:
                if (creature.getDetails().getHunger() >= 100) return false;
                break;
            case THIRST:
                if (creature.getDetails().getThirst() >= 100) return false;
                break;
            case LOVE:
                if (creature.getDetails().getLove() >= 100 ||
                        creature.getDetails().getThirst() < 75 ||
                        creature.getDetails().getHunger() < 75) return false;
                break;
        }

        return true;
    }

    public static boolean isHittable(Creature creature, Set<ItemCode> itemCodes) {
        return itemCodes.stream().anyMatch(itemCode -> isHittable(creature, itemCode));
    }
}
