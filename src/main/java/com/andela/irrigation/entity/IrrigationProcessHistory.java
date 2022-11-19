package com.andela.irrigation.entity;

import com.andela.irrigation.enums.IrrigationProcessStatus;
import com.andela.irrigation.enums.IrrigationType;
import com.andela.irrigation.enums.LiquidUnit;

import java.time.LocalDateTime;

public class IrrigationProcessHistory {
    private Long landId;
    private IrrigationProcessStatus processStatus;
    private IrrigationType irrigationType;
    private Double amountOfWater;
    private LiquidUnit liquidUnit;
    private Integer duration;
    private LocalDateTime createdAt;
}
