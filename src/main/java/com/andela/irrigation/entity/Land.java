package com.andela.irrigation.entity;

import com.andela.irrigation.enums.AgriculturalCropType;
import com.andela.irrigation.enums.AreaUnit;


public class Land {
    private Long id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private Double cultivatedArea;
    private AreaUnit areaUnit;
    private AgriculturalCropType agriculturalCropType;
    private Irrigation irrigation;
}
