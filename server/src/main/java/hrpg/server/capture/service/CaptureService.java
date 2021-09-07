package hrpg.server.capture.service;

import hrpg.server.capture.service.exception.BaitUnavailableException;
import hrpg.server.capture.service.exception.NetUnavailableException;
import hrpg.server.capture.service.exception.RunningCaptureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CaptureService {

    CaptureDto create(int quality, Integer baitLevel) throws NetUnavailableException, RunningCaptureException, BaitUnavailableException;

    Optional<CaptureDto> findById(long id);

    Page<CaptureDto> search(Pageable pageable);

    boolean hasRunningCapture();
}
