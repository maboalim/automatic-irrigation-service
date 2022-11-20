package com.andela.irrigation.service;

import com.andela.irrigation.entity.Irrigation;
import com.andela.irrigation.payload.dto.IrrigationDto;

import java.util.Collection;

public interface IrrigationService {
    IrrigationDto getIrrigationById(Long id);

    IrrigationDto update(IrrigationDto irrigationDto);

    IrrigationDto createNewIrrigation(IrrigationDto irrigationDto, Long landId);

    Collection<Irrigation> getAllReadyIrrigationTriggersAndUpdateToReadyStatus();

    Collection<Irrigation> getAllReadyIrrigationTriggers();

    Irrigation save(Irrigation irrigation);

}
