package com.andela.irrigation.controller;

import com.andela.irrigation.payload.converter.IrrigationConverter;
import com.andela.irrigation.payload.dto.IrrigationDto;
import com.andela.irrigation.payload.request.IrrigationRequest;
import com.andela.irrigation.service.IrrigationService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/irrigations", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class IrrigationController {

    private IrrigationService irrigationService;

    @GetMapping("/{id}")
    public IrrigationDto getIrrigationById(@PathVariable Long id) {
        IrrigationDto irrigation = irrigationService.getIrrigationById(id);
        Link selfLink = linkTo(methodOn(IrrigationController.class)
                .updateIrrigation(irrigation)).withSelfRel();
        irrigation.add(selfLink);
        return irrigation;
    }


    @PostMapping
    public IrrigationDto addNewIrrigation(@Valid @RequestBody IrrigationRequest irrigationRequest) {
        IrrigationDto irrigation = irrigationService.createNewIrrigation(IrrigationConverter.toIrrigationDto(irrigationRequest), irrigationRequest.getLandId());
        setHateoasGetIrrigationLink(irrigation);
        return irrigation;
    }

    @PutMapping
    public IrrigationDto updateIrrigation(@Valid @RequestBody IrrigationDto irrigationDto) {
        IrrigationDto irrigation = irrigationService.update(irrigationDto);
        setHateoasGetIrrigationLink(irrigation);
        return irrigation;
    }

    private void setHateoasGetIrrigationLink(IrrigationDto irrigation) {
        Link selfLink = linkTo(methodOn(IrrigationController.class)
                .getIrrigationById(irrigation.getId())).withSelfRel();
        irrigation.add(selfLink);
    }
}
