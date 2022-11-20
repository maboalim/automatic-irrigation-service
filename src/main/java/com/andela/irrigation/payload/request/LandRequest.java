package com.andela.irrigation.payload.request;

import com.andela.irrigation.enums.AgriculturalCropType;
import com.andela.irrigation.enums.AreaUnit;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder(toBuilder = true)
public class LandRequest {

    @NotNull(message = "Name is mandatory")
    private String name;
    private String description;

    @Max(90)
    @Min(-90)
    private Double latitude;

    @Max(180)
    @Min(-180)
    private Double longitude;

    @NotNull(message = "Cultivated area is mandatory")
    private Double cultivatedArea;

    @NotNull(message = "Area unit is mandatory")
    private AreaUnit areaUnit;

    private AgriculturalCropType agriculturalCropType;

    private IrrigationRequest irrigation;
}
