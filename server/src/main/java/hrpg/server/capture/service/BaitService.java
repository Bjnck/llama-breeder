package hrpg.server.capture.service;

import hrpg.server.capture.service.exception.BaitUnavailableException;

public interface BaitService {

    void decreaseCount(int generation) throws BaitUnavailableException;
}
