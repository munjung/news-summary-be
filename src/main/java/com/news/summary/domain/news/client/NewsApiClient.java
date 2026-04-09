package com.news.summary.domain.news.client;

import com.news.summary.common.client.ApiClient;
import com.news.summary.common.exception.BusinessException;
import com.news.summary.common.exception.ErrorCode;
import com.news.summary.domain.news.dto.NewsApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsApiClient {

    private final ApiClient apiClient;

    @Value("${news.api.key}")
    private String apiKey;

    public List<NewsApiResponse.ArticleDto> fetchEconomyNews() {
        Map<String, Object> params = Map.of(
                "category", "business",
                "language", "en",
                "pageSize", "20",
                "apiKey", apiKey
        );

        NewsApiResponse response = apiClient.get(
                "/top-headlines",
                params,
                NewsApiResponse.class
        );

        if (response == null || !"ok".equals(response.getStatus())) {
            throw new BusinessException(ErrorCode.EXTERNAL_API_ERROR);
        }

        return response.getArticles();
    }
}
