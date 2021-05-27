package hrpg.server.pen.service;

import hrpg.server.pen.dao.PenRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PenServiceImpl implements PenService {

    private final PenRepository penRepository;
    private final PenMapper penMapper;

    public PenServiceImpl(PenRepository penRepository,
                          PenMapper penMapper) {
        this.penRepository = penRepository;
        this.penMapper = penMapper;
    }

    @Override
    public Optional<PenDto> findById(long id) {
        return penRepository.findById(id).map(penMapper::toDto);
    }

    @Override
    public Page<PenDto> search(Pageable pageable) {
        return penRepository.findAll(pageable).map(penMapper::toDto);
    }
}
