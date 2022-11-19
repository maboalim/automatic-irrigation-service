package com.andela.irrigation.entity;

import com.andela.irrigation.enums.IrrigationStatus;
import com.andela.irrigation.enums.IrrigationType;
import com.andela.irrigation.enums.LiquidUnit;

import java.time.LocalDateTime;

public class Irrigation {
    private Long id;
    private IrrigationType irrigationType;
    private Double amountOfWater;
    private LiquidUnit liquidUnit;
    private Integer duration;
    private Double interval;
    private IrrigationStatus irrigationStatus;
    private LocalDateTime nextIrrigationAt;

}
