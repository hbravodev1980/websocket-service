package com.training.websocket.controller;

import com.training.websocket.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/public")
    @SendTo("/topic/messages")
    public Notification send(Notification notification) throws Exception {
        return notification;
    }

    @MessageMapping("/private")
    public void sendToUser(@Payload Notification notification) throws Exception {
        simpMessagingTemplate.convertAndSendToUser(notification.getTo(), "/messages", notification);
    }

}
