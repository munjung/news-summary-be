package com.news.summary.domain.news.controller;

import com.news.summary.common.response.ApiResponse;
import com.news.summary.domain.news.service.NewsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "뉴스 API 연동", description = "뉴스 정보 가져오기")
@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    // 수동으로 뉴스 fetch 트리거 (개발용)
    @PostMapping("/fetch")
    public ApiResponse<String> fetchNews() {
        int count = newsService.fetchAndSaveNews();
        return ApiResponse.success(count + "건 저장되었습니다.");
    }
}