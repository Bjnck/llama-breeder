package hrpg.server.pen.service;

import hrpg.server.pen.resource.PenInfo;
import hrpg.server.pen.service.exception.PriceNotFoundException;

import java.util.List;

public interface PenInfoService {
    List<PenInfo> getInfo();

    int getPurchasePrice(int penCount) throws PriceNotFoundException;

    int getExtendPrice(int penCount, int size) throws PriceNotFoundException;
}
