package ru.nklsfnv.nklsfnvbot.igdb;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nklsfnv.nklsfnvbot.exception.TwitchAuthorizationException;


public class IgdbRequester {

    //TODO store this in Redis
    private volatile String authToken;
    private volatile long tokenExpirationDate = 0;
    private final RestTemplate restTemplate;
    private final String igdbClientId;
    private final String igdbClientSecret;
    private final String igdbApiRootUri;
    private final String twitchAuthUri;
    private final String twitchAuthDefaultGrantType;

    public IgdbRequester(RestTemplate restTemplate, String igdbClientId, String igdbClientSecret, String igdbApiRootUri, String twitchAuthUri, String twitchAuthDefaultGrantType) {
        this.restTemplate = restTemplate;
        this.igdbClientId = igdbClientId;
        this.igdbClientSecret = igdbClientSecret;
        this.igdbApiRootUri = igdbApiRootUri;
        this.twitchAuthUri = twitchAuthUri;
        this.twitchAuthDefaultGrantType = twitchAuthDefaultGrantType;
    }

    public <T> ResponseEntity<T> post(ParameterizedTypeReference<T> responseClass, IgdbApiMethod igdbApiMethod, String body) {
        doAuthorize();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Client-ID", igdbClientId);
        httpHeaders.add("Authorization", "Bearer " + authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);
        return restTemplate.exchange(igdbApiRootUri + igdbApiMethod.getMapping(), HttpMethod.POST, httpEntity, responseClass);
    }

    private void doAuthorize() {
        if (System.currentTimeMillis() < tokenExpirationDate + 200) {
            return;
        }
        synchronized (this) {
            String authUrl = UriComponentsBuilder.fromHttpUrl(twitchAuthUri).queryParam("client_id", igdbClientId).queryParam("client_secret", igdbClientSecret).queryParam("grant_type", twitchAuthDefaultGrantType).toUriString();
            try {
                TwitchToken twitchToken = restTemplate.postForEntity(authUrl, null, TwitchToken.class).getBody();
                if (twitchToken == null) {
                    throw new RuntimeException("twitchToken is null, something went wrong with twitch authentication");
                }
                this.authToken = twitchToken.getAccess_token();
                this.tokenExpirationDate = twitchToken.getExpires_in() * 1000 + System.currentTimeMillis();
            } catch (Exception exception) {
                throw new TwitchAuthorizationException(exception);
            }
        }
    }

}
