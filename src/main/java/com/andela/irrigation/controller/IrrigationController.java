package com.andela.irrigation.controller;

import com.andela.irrigation.payload.converter.IrrigationConverter;
import com.andela.irrigation.payload.dto.IrrigationDto;
import com.andela.irrigation.payload.request.IrrigationRequest;
import com.andela.irrigation.service.IrrigationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/irrigations", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class IrrigationController {

    private IrrigationService irrigationService;

    @GetMapping("/{id}")
    public IrrigationDto getIrrigationById(@PathVariable Long id) {
        return irrigationService.getIrrigationById(id);
    }


    @PostMapping
    public IrrigationDto addNewIrrigation(@Valid @RequestBody IrrigationRequest irrigationRequest) {
        return irrigationService.createNewIrrigation(IrrigationConverter.toIrrigationDto(irrigationRequest), irrigationRequest.getLandId());
    }

    @PutMapping
    public IrrigationDto updateIrrigation(@Valid @RequestBody IrrigationDto irrigationDto) {
        return irrigationService.update(irrigationDto);
    }
}
