package com.andela.irrigation.payload.request;

import com.andela.irrigation.enums.IrrigationStatus;
import com.andela.irrigation.enums.IrrigationType;
import com.andela.irrigation.enums.LiquidUnit;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class IrrigationRequest {

    @NotNull
    private Long landId;
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
}
