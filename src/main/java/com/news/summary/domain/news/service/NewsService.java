package com.news.summary.domain.news.service;

import com.news.summary.domain.news.client.NewsApiClient;
import com.news.summary.domain.news.dto.NewsApiResponse;
import com.news.summary.domain.news.entity.News;
import com.news.summary.domain.news.repository.NewsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsApiClient newsApiClient;

    @Transactional
    public int fetchAndSaveNews() {
        List<NewsApiResponse.ArticleDto> articles = this.newsApiClient.fetchEconomyNews();

        int savedCount = 0;
        for (NewsApiResponse.ArticleDto article : articles) {
            // 중복 저장 방지
            if (this.newsRepository.existsByUrl(article.getUrl())) {
                continue;
            }

            // title이나 url이 null인 기사 스킵 (NewsAPI 간혹 포함)
            if (article.getTitle() == null || article.getUrl() == null) {
                continue;
            }

            LocalDateTime publishedAt = parsePublishedAt(article.getPublishedAt());
            String sourceName = article.getSource() != null ? article.getSource().getName() : null;

            News news = News.of(
                    article.getTitle(),
                    article.getUrl(),
                    article.getUrlToImage(),
                    sourceName,
                    "business",
                    publishedAt
            );
            this.newsRepository.save(news);
            savedCount++;
        }

        log.info("뉴스 저장 완료: {}건", savedCount);
        return savedCount;
    }

    private LocalDateTime parsePublishedAt(String publishedAt) {
        if (publishedAt == null) return LocalDateTime.now();
        try {
            return LocalDateTime.parse(publishedAt, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e) {
            return LocalDateTime.now();
        }
    }
}