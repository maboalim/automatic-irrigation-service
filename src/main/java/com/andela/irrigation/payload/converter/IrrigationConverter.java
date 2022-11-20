package com.andela.irrigation.payload.converter;

import com.andela.irrigation.entity.Irrigation;
import com.andela.irrigation.payload.dto.IrrigationDto;
import com.andela.irrigation.payload.request.IrrigationRequest;

public class IrrigationConverter {

    public static Irrigation toIrrigation(IrrigationDto irrigationDto) {
        return irrigationDto == null ? null : Irrigation.builder().interval(irrigationDto.getInterval())
                .irrigationStatus(irrigationDto.getIrrigationStatus()).irrigationType(irrigationDto.getIrrigationType())
                .nextIrrigationAt(irrigationDto.getNextIrrigationAt()).amountOfWater(irrigationDto.getAmountOfWater())
                .liquidUnit(irrigationDto.getLiquidUnit()).duration(irrigationDto.getDuration()).id(irrigationDto.getId()).build();
    }

    public static IrrigationDto toIrrigationDto(Irrigation irrigation) {
        return irrigation == null ? null : IrrigationDto.builder().interval(irrigation.getInterval())
                .irrigationStatus(irrigation.getIrrigationStatus()).irrigationType(irrigation.getIrrigationType())
                .nextIrrigationAt(irrigation.getNextIrrigationAt()).amountOfWater(irrigation.getAmountOfWater())
                .liquidUnit(irrigation.getLiquidUnit()).duration(irrigation.getDuration()).id(irrigation.getId())
                .createdAt(irrigation.getCreatedAt()).updatedAt(irrigation.getUpdatedAt()).build();
    }

    public static IrrigationDto toIrrigationDto(IrrigationRequest irrigation) {
        return irrigation == null ? null : IrrigationDto.builder().interval(irrigation.getInterval())
                .irrigationStatus(irrigation.getIrrigationStatus()).irrigationType(irrigation.getIrrigationType())
                .nextIrrigationAt(irrigation.getNextIrrigationAt()).amountOfWater(irrigation.getAmountOfWater())
                .liquidUnit(irrigation.getLiquidUnit()).duration(irrigation.getDuration()).build();
    }
}
