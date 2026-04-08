package com.news.summary.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiClient {


    private final WebClient webClient;

    /*
     * GET
     */
    public <T> T get(String url, Class<T> responseType) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                        response.bodyToMono(String.class)
                                .map(errorBody -> {
                                    log.error("GET ERROR - url: {}, body: {}", url, errorBody);
                                    return new RuntimeException("외부 API GET 호출 실패");
                                })
                )
                .bodyToMono(responseType)
                .block();
    }

    /*
     * GET (QueryParam 포함)
     */
    public <T> T get(String url, Map<String, Object> queryParams, Class<T> responseType) {
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(url);
                    queryParams.forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                })
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                        response.bodyToMono(String.class)
                                .map(errorBody -> {
                                    log.error("GET PARAM ERROR - url: {}, params: {}, body: {}", url, queryParams, errorBody);
                                    return new RuntimeException("외부 API GET 호출 실패");
                                })
                )
                .bodyToMono(responseType)
                .block();
    }

    /*
     * POST
     */
    public <T, R> R post(String url, T requestBody, Class<R> responseType) {
        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                        response.bodyToMono(String.class)
                                .map(errorBody -> {
                                    log.error("POST ERROR - url: {}, body: {}, error: {}", url, requestBody, errorBody);
                                    return new RuntimeException("외부 API POST 호출 실패");
                                })
                )
                .bodyToMono(responseType)
                .block();
    }

    /*
     * PUT
     */
    public <T, R> R put(String url, T requestBody, Class<R> responseType) {
        return webClient.put()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                        response.bodyToMono(String.class)
                                .map(errorBody -> {
                                    log.error("PUT ERROR - url: {}, body: {}, error: {}", url, requestBody, errorBody);
                                    return new RuntimeException("외부 API PUT 호출 실패");
                                })
                )
                .bodyToMono(responseType)
                .block();
    }

    /*
     * DELETE
     */
    public void delete(String url) {
        webClient.delete()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                        response.bodyToMono(String.class)
                                .map(errorBody -> {
                                    log.error("DELETE ERROR - url: {}, body: {}", url, errorBody);
                                    return new RuntimeException("외부 API DELETE 호출 실패");
                                })
                )
                .bodyToMono(Void.class)
                .block();
    }
}
