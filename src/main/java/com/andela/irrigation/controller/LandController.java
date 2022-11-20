package com.andela.irrigation.controller;

import com.andela.irrigation.entity.Land;
import com.andela.irrigation.payload.converter.LandConverter;
import com.andela.irrigation.payload.dto.LandDto;
import com.andela.irrigation.payload.request.LandRequest;
import com.andela.irrigation.service.LandService;
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
import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/lands", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class LandController {

    private LandService landService;

    @GetMapping("/{id}")
    public LandDto getLandById(@PathVariable Long id) {
        return landService.getLandById(id);
    }

    @GetMapping()
    public Collection<LandDto> findAllLands() {
        return landService.findAll();
    }

    @PostMapping
    public LandDto addNewLand(@Valid @RequestBody LandRequest landRequest) {
        return landService.save(LandConverter.toLandDto(landRequest));
    }

    @PutMapping
    public LandDto updateLand(@Valid @RequestBody LandDto landDto) {
        return landService.save(landDto);
    }
}
