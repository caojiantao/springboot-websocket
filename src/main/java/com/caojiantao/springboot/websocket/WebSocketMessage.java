package com.caojiantao.springboot.websocket;

import lombok.Data;

@Data
public class WebSocketMessage {

    private String from;

    private String to;

    private String content;
}
