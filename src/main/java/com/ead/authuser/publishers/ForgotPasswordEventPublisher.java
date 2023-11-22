package com.ead.authuser.publishers;

import com.ead.authuser.dtos.ForgotPasswordEventDto;
import com.ead.authuser.enums.ActionType;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForgotPasswordEventPublisher {
    @Value("${ead.broker.exchange.forgotPasswordEvent}")
    private String exchangeForgotPasswordEvent;

    private final RabbitTemplate rabbitTemplate;

    public void publishForgotPasswordEvent(ForgotPasswordEventDto forgotPasswordEventDto, ActionType actionType) {
        forgotPasswordEventDto.setActionType(actionType.name());
        rabbitTemplate.convertAndSend(exchangeForgotPasswordEvent, "", forgotPasswordEventDto);
    }
}
