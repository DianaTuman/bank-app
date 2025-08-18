package com.dianatuman.practicum.notifications.configuration;

import brave.Tracing;
import brave.http.HttpTracing;
import brave.sampler.Sampler;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class TraceConfiguration {

    @Bean
    public Tracing braveTracing(@Value("${spring.application.name}") String appName) {
        return Tracing.newBuilder()
                .localServiceName(appName)
                .sampler(Sampler.ALWAYS_SAMPLE)
                .build();
    }

    @Bean
    public HttpTracing httpTracing(Tracing braveTracing) {
        return HttpTracing.create(braveTracing);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(HttpTracing httpTracing) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(TracingClientHttpRequestInterceptor.create(httpTracing)));
        return restTemplate;
    }

}
