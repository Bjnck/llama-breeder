package hrpg.server.capture.service;

import hrpg.server.capture.service.exception.NestUnavailableException;
import hrpg.server.capture.service.exception.RunningCaptureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CaptureService {
    CaptureDto create(int quality) throws NestUnavailableException, RunningCaptureException;

    Optional<CaptureDto> findById(Long id);

    Page<CaptureDto> search(Pageable pageable);

    boolean hasRunningCapture();
}
