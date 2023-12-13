package com.caojiantao.springboot.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void sendMessage(Principal principal, WebSocketMessage message) {
        log.info("[发送消息] principal={} message={}", principal.getName(), message);
        messagingTemplate.convertAndSendToUser(message.getTo(), "/queue/chat", message);
    }
}
