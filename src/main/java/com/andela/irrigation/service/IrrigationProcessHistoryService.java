package com.andela.irrigation.service;

import com.andela.irrigation.entity.Irrigation;
import com.andela.irrigation.entity.IrrigationProcessHistory;
import com.andela.irrigation.enums.IrrigationProcessStatus;

public interface IrrigationProcessHistoryService {
    IrrigationProcessHistory saveIrrigationRequest(Irrigation irrigation, IrrigationProcessStatus irrigationProcessStatus);
}
