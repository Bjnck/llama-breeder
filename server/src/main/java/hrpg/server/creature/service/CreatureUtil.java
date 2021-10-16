package hrpg.server.creature.service;

import hrpg.server.creature.dao.Creature;
import hrpg.server.item.dao.Item;
import hrpg.server.item.type.ItemCode;

import javax.validation.constraints.NotNull;
import java.util.Set;

import static hrpg.server.creature.type.CreatureConstant.*;

public class CreatureUtil {

    public static boolean isBreedable(Creature creature) {
        return creature.getMaturity() >= MATURITY_MAX &&
                creature.getLove() >= STATS_MAX &&
                creature.getBreedingCount() < BREEDING_MAX &&
                !creature.isPregnant();
    }

    public static boolean isHittable(@NotNull Creature creature, @NotNull ItemCode itemCode, int itemQuality) {
        if (creature.getGeneration() > itemQuality) return false;

        if (creature.getEnergy() <= ENERGY_MIN) return false;
        if (creature.getMaturity() < MATURITY_MAX) return false;

        switch (itemCode) {
            case HUNGER:
                if (creature.getHunger() >= STATS_MAX) return false;
                break;
            case THIRST:
                if (creature.getThirst() >= STATS_MAX) return false;
                break;
            case LOVE:
                if (creature.getLove() >= STATS_MAX ||
                        creature.getThirst() < STATS_LOVE_REQUIREMENT ||
                        creature.getHunger() < STATS_LOVE_REQUIREMENT) return false;
                break;
        }

        return true;
    }

    public static boolean isHittable(Creature creature, Set<Item> items) {
        return items.stream().anyMatch(item -> isHittable(creature, item.getCode(), item.getQuality()));
    }
}
