package com.news.summary.common.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiClient {


    private final WebClient webClient;

    /**
     * baseURL + path 조합
     * @param baseUrl
     * @param path
     * @param responseType
     * @return
     * @param <T>
     */
    public <T> T get(String baseUrl, String path, Class<T> responseType) {

        String fullUrl = baseUrl + path;

        return webClient.get()
                .uri(URI.create(fullUrl)) // 🔥 핵심
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    /**
     * QueryParam
     * @param baseUrl
     * @param path
     * @param queryParams
     * @param responseType
     * @return
     * @param <T>
     */
    public <T> T get(String baseUrl, String path, Map<String, Object> queryParams, Class<T> responseType) {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(baseUrl + path);

        queryParams.forEach(builder::queryParam);

        return webClient.get()
                .uri(builder.build().toUri()) // 🔥 안전
                .retrieve()
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
