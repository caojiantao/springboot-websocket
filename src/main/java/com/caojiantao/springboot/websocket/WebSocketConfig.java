package com.caojiantao.springboot.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 订阅Broker名称:topic 代表发布广播，即群发
        // queue 代表点对点，即发指定用户
        config.enableSimpleBroker("/queue", "/topic");

        // send命令时需要带上/app前缀，@MessageMapping 才会生效
        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
        config.setApplicationDestinationPrefixes("/app");

        // 修改convertAndSendToUser方法前缀
        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），
        // 不设置的话，默认也是/user
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/im")
                .setAllowedOriginPatterns("*")
                // 登录鉴权
                .addInterceptors(new WebSocketInterceptor())
                // 初始化用户态，用在点对点通信
                .setHandshakeHandler(new HandshakeHandleImpl())
                .withSockJS();
    }
}

