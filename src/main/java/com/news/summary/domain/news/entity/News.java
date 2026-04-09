package com.news.summary.domain.news.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    private String urlToImage;
    private String source;
    private String category;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private LocalDateTime publishedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public static News of(String title, String url, String source, String urlToImage,
                          String category, LocalDateTime publishedAt) {
        News news = new News();
        news.title = title;
        news.url = url;
        news.source = source;
        news.urlToImage = urlToImage;
        news.category = category;
        news.publishedAt = publishedAt;
        return news;
    }

    public void updateSummary(String summary) {
        this.summary = summary;
    }
}