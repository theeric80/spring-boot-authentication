package org.theeric.auth.core.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class HttpEntityFactory {

    public static HttpEntity<MultiValueMap<String, String>> formUrlencoded() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        return new HttpEntity<>(body, headers);
    }

    public static <T> HttpEntity<T> json(T body) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<T>(body, headers);
    }

}
