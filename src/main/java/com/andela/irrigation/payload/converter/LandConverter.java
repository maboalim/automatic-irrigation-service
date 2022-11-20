package com.andela.irrigation.payload.converter;

import com.andela.irrigation.entity.Land;
import com.andela.irrigation.payload.dto.LandDto;
import com.andela.irrigation.payload.request.LandRequest;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class LandConverter {

    public static Land toLand(LandDto landDto) {
        return landDto == null ? null : Land.builder().id(landDto.getId()).agriculturalCropType(landDto.getAgriculturalCropType())
                .areaUnit(landDto.getAreaUnit()).cultivatedArea(landDto.getCultivatedArea())
                .description(landDto.getDescription()).name(landDto.getName()).latitude(landDto.getLatitude())
                .longitude(landDto.getLongitude()).irrigation(IrrigationConverter.toIrrigation(landDto.getIrrigation())).build();
    }

    public static LandDto toLandDto(Land land) {
        return land == null ? null : LandDto.builder().id(land.getId()).agriculturalCropType(land.getAgriculturalCropType())
                .areaUnit(land.getAreaUnit()).cultivatedArea(land.getCultivatedArea())
                .description(land.getDescription()).name(land.getName()).latitude(land.getLatitude())
                .longitude(land.getLongitude()).irrigation(IrrigationConverter.toIrrigationDto(land.getIrrigation()))
                .createdAt(land.getCreatedAt()).updatedAt(land.getUpdatedAt()).build();
    }

    public static Collection<LandDto> toLandDto(Collection<Land> lands) {
        return CollectionUtils.isEmpty(lands) ? new ArrayList<>() : lands.stream().map(LandConverter::toLandDto).collect(Collectors.toList());
    }

    public static LandDto toLandDto(LandRequest land) {
        return land == null ? null : LandDto.builder().agriculturalCropType(land.getAgriculturalCropType())
                .areaUnit(land.getAreaUnit()).cultivatedArea(land.getCultivatedArea())
                .description(land.getDescription()).name(land.getName()).latitude(land.getLatitude())
                .longitude(land.getLongitude()).irrigation(IrrigationConverter.toIrrigationDto(land.getIrrigation()))
                .build();
    }
}
