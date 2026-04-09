package com.news.summary.domain.news.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsApiResponse {

    private String status;
    private List<ArticleDto> articles;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ArticleDto {
        private SourceDto source;
        private String title;
        private String description;
        private String url;
        private String urlToImage;
        private String publishedAt;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class SourceDto {
            private String id;
            private String name;
        }
    }
}