package com.kosavpa.first.boot.example.config.vk;


import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;


public class VkOAuth2UserRequestEntityConverter implements Converter<OAuth2UserRequest, RequestEntity<?>> {

    private static final MediaType DEFAULT_CONTENT_TYPE = MediaType
            .valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

    /**
     * Returns the {@link RequestEntity} used for the UserInfo Request.
     * @param userRequest the user request
     * @return the {@link RequestEntity} used for the UserInfo Request
     */
    @Override
    public RequestEntity<?> convert(OAuth2UserRequest userRequest) {
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        HttpMethod httpMethod = getHttpMethod(clientRegistration);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        URI uri = UriComponentsBuilder
                .fromUriString(clientRegistration
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUri() + "user_ids=" + userRequest.getAdditionalParameters().get("user_id") + "&v=5.131")
                .build().toUri();

        RequestEntity<?> request;
        if (HttpMethod.POST.equals(httpMethod)) {
            headers.setContentType(DEFAULT_CONTENT_TYPE);
            MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
            formParameters.add(OAuth2ParameterNames.ACCESS_TOKEN, userRequest.getAccessToken().getTokenValue());
            request = new RequestEntity<>(formParameters, headers, httpMethod, uri);
        }
        else {
            headers.setBearerAuth(userRequest.getAccessToken().getTokenValue());
            request = new RequestEntity<>(headers, httpMethod, uri);
        }

        return request;
    }

    private HttpMethod getHttpMethod(ClientRegistration clientRegistration) {
        if (AuthenticationMethod.FORM
                .equals(clientRegistration.getProviderDetails().getUserInfoEndpoint().getAuthenticationMethod())) {
            return HttpMethod.POST;
        }
        return HttpMethod.GET;
    }
}
