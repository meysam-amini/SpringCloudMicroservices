//package com.meysam.apigateway.config;
//
//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
//import org.springframework.cloud.client.circuitbreaker.Customizer;
//import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
//import org.springframework.cloud.gateway.filter.factory.FallbackHeadersGatewayFilterFactory;
//import org.springframework.cloud.gateway.filter.factory.SpringCloudCircuitBreakerFilterFactory;
//import org.springframework.cloud.gateway.filter.factory.SpringCloudCircuitBreakerResilience4JFilterFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.DispatcherHandler;
//
//@Configuration
//public class Resilience4JConfiguration {
//
//    @Bean
//    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
//        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                .circuitBreakerConfig(CircuitBreakerConfig.custom().minimumNumberOfCalls(1).failureRateThreshold(2).build())
//                .build());
//    }
//
//    @Bean
//    public Customizer<ReactiveResilience4JCircuitBreakerFactory> helloworldCircuitBreaker(){
//        return factory -> {
//            factory.configure(builder -> builder.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()).build()
//                            .setCircuitBreakerConfig(CircuitBreakerConfig.custom().minimumNumberOfCalls(2).failureRateThreshold(1).build())
//                    , "helloworldCircuitBreaker");
//        };
//    }
//
//    @Bean
//    public Customizer<ReactiveResilience4JCircuitBreakerFactory> accountCircuitBreaker(){
//        return factory -> {
//            factory.configure(builder -> builder.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()).build(), "accountCircuitBreaker");
//        };
//    }
//
//    @Bean
//    public FallbackHeadersGatewayFilterFactory fallbackHeadersGatewayFilterFactory() {
//        return new FallbackHeadersGatewayFilterFactory();
//    }
//
//    @Bean
//    public SpringCloudCircuitBreakerFilterFactory resilience4JCircuitBreakerFactory(
//            ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory,
//            ObjectProvider<DispatcherHandler> dispatcherHandlers) {
//        return new SpringCloudCircuitBreakerResilience4JFilterFactory(reactiveCircuitBreakerFactory, dispatcherHandlers);
//    }
//}
