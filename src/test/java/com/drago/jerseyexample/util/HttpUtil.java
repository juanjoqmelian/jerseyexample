package com.drago.jerseyexample.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 16/10/13
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */
public class HttpUtil {

    public static HttpEntity setAcceptHeader(String mediaType){
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Accept", mediaType);
        return new HttpEntity<Object>(null, headers);
    }

    public static HttpEntity setParameter(String name, Object value, MultiValueMap<String, String> headers){
        return new HttpEntity<Object>(value, headers);
    }

}
