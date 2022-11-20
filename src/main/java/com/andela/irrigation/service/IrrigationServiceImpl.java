package com.andela.irrigation.service;

import com.andela.irrigation.entity.Irrigation;
import com.andela.irrigation.entity.Land;
import com.andela.irrigation.payload.converter.IrrigationConverter;
import com.andela.irrigation.payload.dto.IrrigationDto;
import com.andela.irrigation.repository.IrrigationRepository;
import com.andela.irrigation.repository.LandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class IrrigationServiceImpl implements IrrigationService {

    private final IrrigationRepository irrigationRepository;
    private final LandRepository landRepository;

    private Irrigation getById(Long id) {
        return irrigationRepository.getReferenceById(id);
    }

    @Override
    public IrrigationDto getIrrigationById(Long id) {
        return IrrigationConverter.toIrrigationDto(this.getById(id));
    }

    @Override
    public IrrigationDto update(IrrigationDto irrigationDto) {
        Irrigation currentIrrigation = getById(irrigationDto.getId());
        Irrigation irrigation = IrrigationConverter.toIrrigation(irrigationDto);
        irrigation.setUpdatedAt(LocalDateTime.now());

        //Not allowed to update created At field
        irrigation.setCreatedAt(currentIrrigation.getCreatedAt());
        return IrrigationConverter.toIrrigationDto(this.save(irrigation));
    }

    @Override
    public IrrigationDto createNewIrrigation(IrrigationDto irrigationDto, Long landId) {
        LocalDateTime now = LocalDateTime.now();
        Land land = landRepository.getReferenceById(landId);
        land.setIrrigation(IrrigationConverter.toIrrigation(irrigationDto));
        land.getIrrigation().setCreatedAt(now);
        land.getIrrigation().setUpdatedAt(now);
        land.setUpdatedAt(now);
        return IrrigationConverter.toIrrigationDto(landRepository.save(land).getIrrigation());
    }

    private Irrigation save(Irrigation irrigation) {
        return irrigationRepository.save(irrigation);
    }
}
