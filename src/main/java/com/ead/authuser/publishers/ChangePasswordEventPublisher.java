package com.ead.authuser.publishers;

import com.ead.authuser.dtos.ChangePasswordEventDto;
import com.ead.authuser.enums.ActionType;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangePasswordEventPublisher {
    @Value("${ead.broker.exchange.changePasswordEvent}")
    private String exchangeChangePasswordEvent;

    private final RabbitTemplate rabbitTemplate;

    public void publishChangePasswordEvent(ChangePasswordEventDto changePasswordEventDto, ActionType actionType) {
        changePasswordEventDto.setActionType(actionType.name());
        rabbitTemplate.convertAndSend(exchangeChangePasswordEvent, "", changePasswordEventDto);
    }
}
