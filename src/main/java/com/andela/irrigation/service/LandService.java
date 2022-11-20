package com.andela.irrigation.service;

import com.andela.irrigation.entity.Land;
import com.andela.irrigation.payload.dto.LandDto;

import java.util.Collection;

public interface LandService {

    LandDto getLandById(Long id);

    Collection<LandDto> findAll();

    LandDto save(LandDto landDto);
}
