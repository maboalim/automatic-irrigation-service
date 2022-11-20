package com.andela.irrigation.service.impl;

import com.andela.irrigation.entity.Irrigation;
import com.andela.irrigation.entity.IrrigationProcessHistory;
import com.andela.irrigation.enums.IrrigationProcessStatus;
import com.andela.irrigation.repository.IrrigationProcessHistoryRepository;
import com.andela.irrigation.service.IrrigationProcessHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class IrrigationProcessHistoryServiceImpl implements IrrigationProcessHistoryService {

    private final IrrigationProcessHistoryRepository irrigationProcessHistoryRepository;


    @Override
    public IrrigationProcessHistory saveIrrigationRequest(Irrigation irrigation, IrrigationProcessStatus irrigationProcessStatus) {
        IrrigationProcessHistory irrigationProcessHistory = IrrigationProcessHistory.builder().
                irrigationId(irrigation.getId()).irrigationType(irrigation.getIrrigationType())
                .amountOfWater(irrigation.getAmountOfWater()).liquidUnit(irrigation.getLiquidUnit())
                .createdAt(LocalDateTime.now()).duration(irrigation.getDuration())
                .processStatus(irrigationProcessStatus).build();
        return irrigationProcessHistoryRepository.save(irrigationProcessHistory);
    }

}
