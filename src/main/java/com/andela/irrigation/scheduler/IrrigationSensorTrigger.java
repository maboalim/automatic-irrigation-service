package com.andela.irrigation.scheduler;

import com.andela.irrigation.entity.Irrigation;
import com.andela.irrigation.entity.IrrigationProcessHistory;
import com.andela.irrigation.enums.ErrorCode;
import com.andela.irrigation.enums.IrrigationProcessStatus;
import com.andela.irrigation.enums.IrrigationStatus;
import com.andela.irrigation.feign.FeignIrrigationSensorService;
import com.andela.irrigation.payload.converter.IrrigationConverter;
import com.andela.irrigation.payload.dto.IrrigationDto;
import com.andela.irrigation.service.EmailService;
import com.andela.irrigation.service.IrrigationProcessHistoryService;
import com.andela.irrigation.service.IrrigationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@Configuration
@EnableScheduling
@AllArgsConstructor
@Transactional
@Log4j2
public class IrrigationSensorTrigger {

    private final IrrigationService irrigationService;
    private final FeignIrrigationSensorService feignIrrigationSensorService;

    private final IrrigationProcessHistoryService irrigationProcessHistoryService;

    private final EmailService emailService;


    @Scheduled(fixedDelayString = "${irrigation-service.irrigation-check-scheduler}")
    public void run() {
        startProcessingIrrigationTriggers();
    }

    private void startProcessingIrrigationTriggers() {
        Collection<Irrigation> irrigations = irrigationService.getAllReadyIrrigationTriggersAndUpdateToReadyStatus();
        for (Irrigation irrigation : irrigations) {
            log.info("Processing sending trigger to sensor for irrigation id= {}", irrigation.getId());
            try {
                boolean isSuccess = sendIrrigationOrderToSensor(irrigation);
                if (isSuccess) {
                    updateIrrigationHistory(irrigation, IrrigationProcessStatus.SUCCESS);
                    updateIrrigationStatus(irrigation, IrrigationStatus.ACTIVE);
                } else {
                    handleFailureProcedures(irrigation, ErrorCode.IRRIGATION_TRIGGER_FAILED.getErrorMessage());
                }

            } catch (Exception ex) {
                log.error("Failure while sending trigger to sensor for irrigation id= {}, with exception", irrigation.getId(), ex);
                sendFailureEventAlarm(irrigation, ex.getMessage() == null ? ex.getCause().toString() : ex.getMessage());
            }

        }
    }

    private void handleFailureProcedures(Irrigation irrigation, String errorMessage) {
        updateIrrigationHistory(irrigation, IrrigationProcessStatus.FAILED);
        updateIrrigationStatus(irrigation, IrrigationStatus.OUT_OF_SERVICE);
        sendFailureEventAlarm(irrigation, errorMessage);
    }

    private void updateIrrigationHistory(Irrigation irrigation, IrrigationProcessStatus status) {
        IrrigationProcessHistory irrigationProcessHistory = irrigationProcessHistoryService.saveIrrigationRequest(irrigation, status);
    }

    private boolean sendIrrigationOrderToSensor(Irrigation irrigation) {
        ResponseEntity<?> response = feignIrrigationSensorService.triggerIrrigationSensor(IrrigationConverter.toIrrigationDto(irrigation));
        return response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.ACCEPTED;
    }

    public boolean irrigationTriggerFallback(Irrigation irrigation, Exception e) {
        return false;
    }

    private void sendFailureEventAlarm(IrrigationDto irrigationDto, String errorMessage) {
        emailService.sendSensorFailureAlarm(irrigationDto, errorMessage);
    }

    private void sendFailureEventAlarm(Irrigation irrigation, String errorMessage) {
        this.sendFailureEventAlarm(IrrigationConverter.toIrrigationDto(irrigation), errorMessage);
    }

    private void updateIrrigationStatus(Irrigation irrigation, IrrigationStatus status) {
        LocalDateTime currentTime = LocalDateTime.now();
        irrigation.setUpdatedAt(LocalDateTime.now());
        irrigation.setNextIrrigationAt(currentTime.plusMinutes((long) (irrigation.getInterval() / 60)));
        irrigation.setIrrigationStatus(status);
        irrigationService.save(irrigation);
    }

}
