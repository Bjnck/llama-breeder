package hrpg.server.capture.service;

import hrpg.server.capture.service.exception.NestUnavailableException;
import hrpg.server.user.service.exception.TooManyCaptureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CaptureService {
    CaptureDto create(int quality) throws TooManyCaptureException, NestUnavailableException;

    void updateFlagAndFillEmpty();

    Optional<CaptureDto> findById(String id);

    Page<CaptureDto> search(Pageable pageable);

    Optional<CaptureDto> findRunning();
}
