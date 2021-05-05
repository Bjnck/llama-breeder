package hrpg.server.creature.resource;

import hrpg.server.creature.type.Sex;

public interface CreatureQueryParams {
    Sex getSex();
    Integer getGeneration();
    String getColor1();
    String getColor2();
    String getGene1();
    String getGene2();
    Boolean getWild();
    Boolean getPregnant();
}
