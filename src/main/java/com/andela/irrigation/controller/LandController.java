package com.andela.irrigation.controller;

import com.andela.irrigation.payload.converter.LandConverter;
import com.andela.irrigation.payload.dto.LandDto;
import com.andela.irrigation.payload.request.LandRequest;
import com.andela.irrigation.service.LandService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
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
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/lands", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class LandController {

    private LandService landService;

    @GetMapping("/{id}")
    public LandDto getLandById(@PathVariable Long id) {
        LandDto land = landService.getLandById(id);

        Link selfLink = linkTo(methodOn(LandController.class)
                .updateLand(land)).withSelfRel();
        land.add(selfLink);
        return land;
    }

    @GetMapping()
    public CollectionModel<LandDto> findAllLands() {
        Collection<LandDto> lands = landService.findAll();

        for (LandDto land : lands) {
            setHateoasGetLandLink(land);

        }
        Link link = linkTo(LandController.class).withSelfRel();
        CollectionModel<LandDto> result = CollectionModel.of(lands, link);
        return result;
    }

    @PostMapping
    public LandDto addNewLand(@Valid @RequestBody LandRequest landRequest) {
        LandDto land = landService.save(LandConverter.toLandDto(landRequest));
        setHateoasGetLandLink(land);
        return land;
    }

    @PutMapping
    public LandDto updateLand(@Valid @RequestBody LandDto landDto) {
        LandDto land = landService.save(landDto);
        setHateoasGetLandLink(land);
        return land;
    }

    private void setHateoasGetLandLink(LandDto land) {
        Link selfLink = linkTo(methodOn(LandController.class)
                .getLandById(land.getId())).withSelfRel();
        land.add(selfLink);
    }
}
