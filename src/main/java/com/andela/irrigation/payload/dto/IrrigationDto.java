package com.andela.irrigation.payload.dto;

import com.andela.irrigation.enums.IrrigationStatus;
import com.andela.irrigation.enums.IrrigationType;
import com.andela.irrigation.enums.LiquidUnit;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class IrrigationDto extends RepresentationModel<IrrigationDto> {
    private Long id;

    private IrrigationType irrigationType;

    @NotNull
    private Double amountOfWater;

    @NotNull
    private LiquidUnit liquidUnit;

    @NotNull
    private Integer duration;

    @NotNull
    private Double interval;

    private IrrigationStatus irrigationStatus;

    private LocalDateTime nextIrrigationAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
