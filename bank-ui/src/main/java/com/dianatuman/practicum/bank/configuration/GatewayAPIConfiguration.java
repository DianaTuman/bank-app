package com.dianatuman.practicum.bank.configuration;

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

@Slf4j
@Configuration
public class GatewayAPIConfiguration {

    @Value("${gateway_api_url}")
    private String gatewayURL;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(OAuth2AuthorizedClientManager authorizedClientManager) {
        var token = authorizedClientManager.authorize(
                OAuth2AuthorizeRequest
                        .withClientRegistrationId("gateway-api")
                        .principal("system")
                        .build()).getAccessToken().getTokenValue();
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory(gatewayURL);
        return new RestTemplateBuilder()
                .uriTemplateHandler(defaultUriBuilderFactory)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();

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
