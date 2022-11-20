package com.andela.irrigation.service;

import com.andela.irrigation.payload.dto.IrrigationDto;

public interface EmailService {
    void sendSensorFailureAlarm(IrrigationDto irrigationDto, String errorMessage);
}
