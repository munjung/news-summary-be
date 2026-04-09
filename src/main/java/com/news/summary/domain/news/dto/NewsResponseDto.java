package com.news.summary.domain.news.dto;

import com.news.summary.domain.news.entity.News;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NewsResponseDto {

    private Long id;
    private String title;
    private String url;
    private String source;
    private String category;
    private String summary;
    private String imageUrl;
    private LocalDateTime publishedAt;

    public static NewsResponseDto from(News news) {
        return new NewsResponseDto(
                news.getId(),
                news.getTitle(),
                news.getUrl(),
                news.getSource(),
                news.getCategory(),
                news.getSummary(),
                news.getUrlToImage(),
                news.getPublishedAt()
        );
    }
}
