package com.github.fabriciolfj.cursesocket;

import com.github.fabriciolfj.cursesocket.model.InputMessage;
import com.github.fabriciolfj.cursesocket.model.OutputMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.Clock;
import java.time.Instant;

@Slf4j
@Controller
public class MessageController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage message(final InputMessage message) {
        log.info("Mensagem de entrada {}", message);
        return OutputMessage
                .builder()
                .time(Instant.now(Clock.systemDefaultZone()))
                .content(message.getContent())
                .build();
    }
}
