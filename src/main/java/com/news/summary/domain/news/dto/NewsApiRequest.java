package com.news.summary.domain.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewsApiRequest {
    private String from;
    private String to;
}
