package hrpg.server.creature.service;

public class CreatureUtil {

    public static boolean isBreedable(int maturity, int love) {
        return maturity >= 100 && love >= 100;
    }
}
