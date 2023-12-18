package com.meysam.notification.service.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class WebClientConfig {

    @Value("${web.client.time-out.response:#{10000}}")
    private int WEB_CLIENT_RESPONSE_TIME_OUT;

    @Value("${web.client.time-out.connection:#{10000}}")
    private int WEB_CLIENT_CONNECTION_TIME_OUT;

    @Bean
    public WebClient webClient(){
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, WEB_CLIENT_CONNECTION_TIME_OUT)
                .responseTimeout(Duration.ofMillis(WEB_CLIENT_RESPONSE_TIME_OUT))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(WEB_CLIENT_CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(WEB_CLIENT_RESPONSE_TIME_OUT, TimeUnit.MILLISECONDS)));

        WebClient client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
        return client;
    }
}
