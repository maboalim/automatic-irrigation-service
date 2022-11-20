package com.andela.irrigation.service.impl;

import com.andela.irrigation.entity.Land;
import com.andela.irrigation.exception.ResourceNotFoundException;
import com.andela.irrigation.payload.converter.LandConverter;
import com.andela.irrigation.payload.dto.LandDto;
import com.andela.irrigation.repository.LandRepository;
import com.andela.irrigation.service.LandService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor
@Service
@Log4j2
public class LandServiceImpl implements LandService {
    private final LandRepository landRepository;

    @Override
    public LandDto getLandById(Long id) {
        Land land = landRepository.getReferenceById(id);
        log.debug("landId={}, with unit area={}", land.getId(), land.getAreaUnit());
        return LandConverter.toLandDto(land);
    }

    @Override
    public Collection<LandDto> findAll() {
        Collection<Land> lands = landRepository.findAll();
        if (CollectionUtils.isEmpty(lands)) {
            throw new ResourceNotFoundException("Invalid id");
        }
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
            //create new land
            land.setCreatedAt(now);
            if (land.getIrrigation() != null) {
                land.getIrrigation().setCreatedAt(now);
                if (land.getIrrigation().getNextIrrigationAt() == null) {
                    land.getIrrigation().setNextIrrigationAt(LocalDateTime.now());
                }
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
