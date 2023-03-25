package ru.nklsfnv.nklsfnvbot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.nklsfnv.nklsfnvbot.igdb.IgdbRequester;

import java.time.Duration;

@Configuration
public class IgdbConfiguration {

    @Value("${igdb.api.root_uri}")
    private String igdbApiRootUri;
    @Value("${igdb.timeout_value}")
    private long timeout;

    @Value("${igdb.api.client_id}")
    private String clientId;

    @Value("${igdb.api.client_secret}")
    private String clientSecret;

    @Value("${igdb.twitch.auth_uri}")
    private String twitchAuthUrl;

    @Value("${igdb.twitch.auth_default_grant_type}")
    private String twitchAuthDefaultGrantType;

    @Bean
    public IgdbRequester igdbRequester(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate igdbRestTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(timeout))
                .setReadTimeout(Duration.ofSeconds(timeout))
                .build();
        return new IgdbRequester(
                igdbRestTemplate,
                clientId,
                clientSecret,
                igdbApiRootUri,
                twitchAuthUrl,
                twitchAuthDefaultGrantType
        );
    }

}
