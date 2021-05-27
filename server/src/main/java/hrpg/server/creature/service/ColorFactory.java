package hrpg.server.creature.service;

import hrpg.server.creature.dao.Color;

public interface ColorFactory {

    Color getForCapture(String previousColorCode);
}
