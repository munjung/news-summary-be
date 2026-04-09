package com.news.summary.common.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Slf4j
@Component
public class ApiClient {

    private final WebClient newsWebClient;
    private final WebClient openAiWebClient;

    public ApiClient(
            @Qualifier("newsWebClient") WebClient newsWebClient,
            @Qualifier("openAiWebClient") WebClient openAiWebClient
    ) {
        this.newsWebClient = newsWebClient;
        this.openAiWebClient = openAiWebClient;
    }

    // NewsApiClient에서 쓰는 메서드 — 그대로 유지
    public <T> T get(String url, Map<String, Object> queryParams, Class<T> responseType) {
        return newsWebClient.get()
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

    // OpenAiClient에서 쓰는 메서드
    public <T, R> R postToOpenAi(String url, T requestBody, Class<R> responseType) {
        return openAiWebClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                        response.bodyToMono(String.class)
                                .map(errorBody -> {
                                    log.error("OPENAI POST ERROR - url: {}, body: {}", url, errorBody);
                                    return new RuntimeException("OpenAI API 호출 실패");
                                })
                )
                .bodyToMono(responseType)
                .block();
    }
}