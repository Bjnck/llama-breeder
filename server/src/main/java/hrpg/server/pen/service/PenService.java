package hrpg.server.pen.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PenService {
    Optional<PenDto> findById(long id);

    Page<PenDto> search(Pageable pageable);
}
