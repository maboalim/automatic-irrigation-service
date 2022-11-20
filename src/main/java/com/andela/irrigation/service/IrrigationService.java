package com.andela.irrigation.service;

import com.andela.irrigation.payload.dto.IrrigationDto;

public interface IrrigationService {
    IrrigationDto getIrrigationById(Long id);

    IrrigationDto update(IrrigationDto irrigationDto);

    IrrigationDto createNewIrrigation(IrrigationDto irrigationDto, Long landId);
}
