package com.sap.clm.sl.cias.kyma.KymaPilot.utils;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class CustomHTTPRequest {

    private static final Logger oLogger = LoggerFactory.getLogger(CustomHTTPRequest.class);

    @Getter
    @Setter
    private String name;
    @Setter
    @Getter(AccessLevel.PRIVATE)
    private String baseURL;
    @Setter
    @Getter(AccessLevel.PRIVATE)
    private String path;
    @Getter
    @Setter
    private Map<String, Object> params;

    @Getter
    @Setter
    private HttpMethod httpMethod;
    @Getter
    @Setter
    private Class<?> returnClassType;
    @Setter
    @Getter(AccessLevel.PRIVATE)
    private Map<String, String> headers;
    @Getter
    @Setter
    private Object body;
    @Getter
    @Setter
    private Map<String, ?> pathVariables;

    public URI getURI() throws CustomHTTPException {

        String url = "";

        if (this.getBaseURL() == null || this.getBaseURL().isEmpty()) {
            throw new CustomHTTPException("ERROR: BaseURL cannot be empty");
        } else {
            url = this.getBaseURL();
        }
        if (this.getPath() == null || this.getPath().isEmpty()) {
            oLogger.debug("PATH is empty");
        } else {
            url = baseURL.concat(this.getPath());
        }

        UriBuilder builder;
        try {
            builder = UriComponentsBuilder.fromHttpUrl(url);
        } catch (Exception e) {
            throw new CustomHTTPException("ERROR: Unable to create URI for the URL: ".concat(url));
        }
        try {
            MultiValueMap<String, String> paramsList = new LinkedMultiValueMap<>();
            if (this.getParams() != null && !this.getParams().isEmpty()) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue()!=null?entry.getValue().toString():null;
                    paramsList.add(key, value);
                }
                builder.queryParams(paramsList);
            }


        } catch (Exception e) {
            throw new CustomHTTPException("ERROR: Unable to create header Parameters for the URL: ".concat(url));
        }

        try {
            if(this.getPathVariables()!=null && !this.getPathVariables().isEmpty()) {
                return builder.build(this.getPathVariables());
            }else {
                return builder.build();
            }

        } catch (Exception e) {
            throw new CustomHTTPException("ERROR: Unable to build URI with builder for the URL: ".concat(url));
        }

    }

    public Consumer<HttpHeaders> getHeaderList() throws CustomHTTPException {
        Consumer<HttpHeaders> httpheaders = null;
        if (this.getHeaders() != null && !this.getHeaders().isEmpty()) {
            httpheaders =  (HttpHeaders h) -> h.setAll(this.getHeaders());

            Collection<Entry<String, String>> headersList = this.getHeaders().entrySet();

            for (Entry<String, String> header : headersList) {
                String key = header.getKey();
                String value = header.getValue();

            }
        }
        else {
            httpheaders =  (HttpHeaders h) -> h.setAll(new HashMap<>());
        }

        return httpheaders;
    }

}
