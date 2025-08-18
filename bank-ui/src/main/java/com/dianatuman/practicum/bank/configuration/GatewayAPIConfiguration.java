package com.dianatuman.practicum.bank.configuration;

import brave.Tracing;
import brave.http.HttpTracing;
import brave.sampler.Sampler;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Collections;

@Slf4j
@Configuration
public class GatewayAPIConfiguration {

    @Value("${gateway_api_url}")
    private String gatewayURL;

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
    public RestTemplate restTemplate(OAuth2AuthorizedClientManager authorizedClientManager, HttpTracing httpTracing) {
        var token = authorizedClientManager.authorize(
                OAuth2AuthorizeRequest
                        .withClientRegistrationId("gateway-api")
                        .principal("system")
                        .build()).getAccessToken().getTokenValue();
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory(gatewayURL);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .uriTemplateHandler(defaultUriBuilderFactory)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
        restTemplate.setInterceptors(Collections.singletonList(TracingClientHttpRequestInterceptor.create(httpTracing)));
        return restTemplate;
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService clientService) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .refreshToken()
                        .clientCredentials()
                        .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, clientService);

        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }
}
