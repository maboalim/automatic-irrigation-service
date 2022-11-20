package com.andela.irrigation.service;

import com.andela.irrigation.entity.Land;
import com.andela.irrigation.exception.ResourceNotFoundException;
import com.andela.irrigation.payload.converter.LandConverter;
import com.andela.irrigation.payload.dto.LandDto;
import com.andela.irrigation.repository.LandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor
@Service
public class LandServiceImpl implements LandService {
    private final LandRepository landRepository;

    @Override
    public LandDto getLandById(Long id) {
        Land land = landRepository.getReferenceById(id);
        if (land == null) {
            throw new ResourceNotFoundException("Invalid id");
        } else {
            land.getId();
            land.getAreaUnit();
            land.getIrrigation();
        }
        return LandConverter.toLandDto(land);
    }

    @Override
    public Collection<LandDto> findAll() {
        Collection<Land> lands = landRepository.findAll();
        if (CollectionUtils.isEmpty(lands)) {
            throw new ResourceNotFoundException("Invalid id");
        }
        lands.stream().findFirst().get().getId();
        return LandConverter.toLandDto(lands);
    }

    private Land save(Land land) {
        return landRepository.save(land);
    }

    @Override
    public LandDto save(LandDto landDto) {
        LocalDateTime now = LocalDateTime.now();
        Land land = LandConverter.toLand(landDto);
        if (land.getId() == null) {
            land.setCreatedAt(now);
            if (land.getIrrigation() != null) {
                land.getIrrigation().setCreatedAt(now);
            }
        } else {
            //Not allowed to update created At field
            Land currentLand = landRepository.getReferenceById(land.getId());
            land.setCreatedAt(currentLand.getCreatedAt());
            if (land.getIrrigation() != null) {
                land.getIrrigation().setCreatedAt(currentLand.getCreatedAt());
            }
        }
        land.setUpdatedAt(now);
        if (land.getIrrigation() != null) {
            land.getIrrigation().setUpdatedAt(now);

        }
        return LandConverter.toLandDto(save(land));
    }
}
