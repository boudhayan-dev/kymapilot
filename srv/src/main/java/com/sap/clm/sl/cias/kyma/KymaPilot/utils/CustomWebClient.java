package com.sap.clm.sl.cias.kyma.KymaPilot.utils;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;



import reactor.core.publisher.Mono;

@Service
public class CustomWebClient {

    private static final Logger oLogger = LoggerFactory.getLogger(CustomWebClient.class);

    private static final String TRANSFER_ENCODING = "Transfer-Encoding";

//    @Autowired
//    WebClient webClient;

    public Mono<?> retrieve(CustomHTTPRequest request) throws CustomHTTPException {
        this.logRequestMetadata(request);

        return WebClient.create()
                .get()
                .uri(request.getURI())
                .headers(request.getHeaderList())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    oLogger.error("ERROR: RETRIEVING DATA FOR REQUEST {} FAILED: CLIENT SIDE: {}", request.getName(), response.statusCode());
                    return Mono.error(new CustomHTTPException("Request failed on the Client side: 4xx Error"));
                }).onStatus(HttpStatus::is5xxServerError, response -> {
                    oLogger.error("ERROR: RETRIEVING DATA FOR REQUEST {} FAILED: CLIENT SIDE: {}: WITH ERROR: {}", request.getName(), response.statusCode(), response.bodyToMono(String.class));
                    return Mono.error(new CustomHTTPException("Request failed on the Server side: 5xx Error"));
                })
                .bodyToMono(request.getReturnClassType());

    }

    public Mono<?> exchange(CustomHTTPRequest request) throws CustomHTTPException {
        this.logRequestMetadata(request);
        return WebClient.create()
                .get()
                .uri(request.getURI())
                .headers(request.getHeaderList())
                .exchange()
                .map(response -> {
                    HttpHeaders headers = getResponseHeaders(response.headers().asHttpHeaders());

                    oLogger.info("Request for call '{}' retunred response: {}", request.getName(), response.statusCode());

                    return ResponseEntity
                            .status(response.statusCode())
                            .headers(headers)
                            .body(response.bodyToMono(request.getReturnClassType()));
                });
    }

    public Mono<?> create(CustomHTTPRequest request) throws CustomHTTPException {
        this.logRequestMetadata(request);
        return WebClient.create().post().uri(request.getURI()).headers(request.getHeaderList())
                .body(Mono.just(request.getBody()), request.getReturnClassType()).retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    oLogger.error("ERROR: RETRIEVING DATA FOR REQUEST {} FAILED: CLIENT SIDE: {}", request.getName(), response.statusCode());
                    return Mono.error(new CustomHTTPException("Request failed on the Client side: 4xx Error"));
                }).onStatus(HttpStatus::is5xxServerError, response -> {
                    oLogger.error("ERROR: RETRIEVING DATA FOR REQUEST {} FAILED: CLIENT SIDE: {}", request.getName(), response.statusCode());
                    return Mono.error(new CustomHTTPException("Request failed on the Server side: 5xx Error"));
                }).bodyToMono(request.getReturnClassType());

    }

    public Mono<?> update(CustomHTTPRequest request) throws CustomHTTPException {
        this.logRequestMetadata(request);
        return WebClient.create().put().uri(request.getURI()).headers(request.getHeaderList())
                .body(Mono.just(request.getBody()), request.getReturnClassType()).retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    oLogger.error("ERROR: RETRIEVING DATA FOR REQUEST {} FAILED: CLIENT SIDE: {}", request.getName(), response.statusCode());
                    return Mono.error(new CustomHTTPException("Request failed on the Client side: 4xx Error"));
                }).onStatus(HttpStatus::is5xxServerError, response -> {
                    oLogger.error("ERROR: RETRIEVING DATA FOR REQUEST {} FAILED: CLIENT SIDE: {}", request.getName(), response.statusCode());
                    return Mono.error(new CustomHTTPException("Request failed on the Server side: 5xx Error"));
                }).bodyToMono(request.getReturnClassType());

    }

    public Mono<?> patch(CustomHTTPRequest request) throws CustomHTTPException {
        this.logRequestMetadata(request);
        return WebClient.create().patch().uri(request.getURI()).headers(request.getHeaderList())
                .body(Mono.just(request.getBody()), request.getReturnClassType()).retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    oLogger.error("ERROR: RETRIEVING DATA FOR REQUEST {} FAILED: CLIENT SIDE: {}", request.getName(), response.statusCode());
                    return Mono.error(new CustomHTTPException("Request failed on the Client side: 4xx Error"));
                }).onStatus(HttpStatus::is5xxServerError, response -> {
                    oLogger.error("ERROR: RETRIEVING DATA FOR REQUEST {} FAILED: CLIENT SIDE: {}", request.getName(), response.statusCode());
                    return Mono.error(new CustomHTTPException("Request failed on the Server side: 5xx Error"));
                }).bodyToMono(request.getReturnClassType());

    }

    public Mono<?> delete(CustomHTTPRequest request) throws CustomHTTPException {
        this.logRequestMetadata(request);
        if(request.getBody() == null)
            request.setBody("{}");

        return WebClient.create().method(HttpMethod.DELETE).uri(request.getURI()).headers(request.getHeaderList())
                .body(Mono.just(request.getBody()), request.getReturnClassType())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    oLogger.error("ERROR: RETRIEVING DATA FOR REQUEST {} FAILED: CLIENT SIDE: {}", request.getName(), response.statusCode());
                    return Mono.error(new CustomHTTPException("Request failed on the Client side: 4xx Error"));
                }).onStatus(HttpStatus::is5xxServerError, response -> {
                    oLogger.error("ERROR: RETRIEVING DATA FOR REQUEST {} FAILED: CLIENT SIDE: {}", request.getName(), response.statusCode());
                    return Mono.error(new CustomHTTPException("Request failed on the Server side: 5xx Error"));
                }).bodyToMono(request.getReturnClassType());

    }

    private HttpHeaders getResponseHeaders(HttpHeaders headers) {
        return headers.entrySet().stream()
                .filter(entry -> !entry.getKey().equalsIgnoreCase(TRANSFER_ENCODING))
                .collect(HttpHeaders::new, (httpHeaders, entry) -> httpHeaders.addAll(entry.getKey(), entry.getValue()), HttpHeaders::putAll);
    }


    private void logRequestMetadata(CustomHTTPRequest request) throws CustomHTTPException {
        // oLogger.debug("******* HTTP Request Metadata ******** \n\n {}", request.toString());
    }

}
