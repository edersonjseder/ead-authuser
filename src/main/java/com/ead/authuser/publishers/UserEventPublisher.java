package com.ead.authuser.publishers;

import com.ead.authuser.dtos.UserEventDto;
import com.ead.authuser.enums.ActionType;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPublisher {
    @Value("${ead.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    private final RabbitTemplate rabbitTemplate;

    public void publishUserEvent(UserEventDto userEventDto, ActionType actionType) {
        userEventDto.setActionType(actionType.name());
        rabbitTemplate.convertAndSend(exchangeUserEvent, "", userEventDto);
    }
}
