package com.seongho.spring.slack.event;

import com.seongho.spring.slack.enums.CommandType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SlackNotificationEvent extends ApplicationEvent {

    private final CommandType type;
    private final String message;

    public SlackNotificationEvent(Object source, CommandType type, String message) {
        super(source);
        this.type = type;
        this.message = message;
    }

}
