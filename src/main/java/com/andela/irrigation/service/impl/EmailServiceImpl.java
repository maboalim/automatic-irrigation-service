package com.andela.irrigation.service.impl;

import com.andela.irrigation.payload.dto.IrrigationDto;
import com.andela.irrigation.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Value("${irrigation-service.mail.from}")
    String from;

    @Value("${irrigation-service.mail.to}")
    String to;

    @Override
    public void sendSensorFailureAlarm(IrrigationDto irrigationDto, String errorMessage) {
        sendSimpleMessage(from, to, "Failure while processing irrigation Id=" + irrigationDto.getId(), "Failure while process irrigation Id=" + irrigationDto.getId() + ", at=" + irrigationDto.getNextIrrigationAt() + "with error=" + errorMessage);
    }


    private void sendSimpleMessage(String from, String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
