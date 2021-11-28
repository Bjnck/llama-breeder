package hrpg.server.capture.service;

import hrpg.server.capture.service.exception.CaptureNotFoundException;
import hrpg.server.capture.service.exception.NetUnavailableException;
import hrpg.server.capture.service.exception.RunningCaptureException;
import hrpg.server.creature.service.exception.MaxCreaturesException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CaptureService {

    CaptureDto create(int quality) throws NetUnavailableException, RunningCaptureException;

    Optional<CaptureDto> findById(long id);

    Page<CaptureDto> search(Pageable pageable);

    boolean hasRunningCapture();

    CaptureDto redeem(long id) throws CaptureNotFoundException, RunningCaptureException, MaxCreaturesException;
}
