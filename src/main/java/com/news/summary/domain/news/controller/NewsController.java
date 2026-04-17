package com.news.summary.domain.news.controller;

import com.news.summary.common.response.ApiResponse;
import com.news.summary.domain.news.dto.NewsApiRequest;
import com.news.summary.domain.news.dto.NewsResponseDto;
import com.news.summary.domain.news.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "뉴스 API 연동", description = "뉴스 정보 가져오기")
@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "뉴스 목록 조회", description = "뉴스 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<Page<NewsResponseDto>> getNewsList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.success(this.newsService.getNewsList(page, size));
    }

    @Operation(summary = "뉴스 상세 조회", description = "뉴스 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ApiResponse<NewsResponseDto> getNewsDetail(@PathVariable Long id) {
        return ApiResponse.success(this.newsService.getNewsDetail(id));
    }

    @Operation(summary = "뉴스 fetch", description = "NewsApi, OpenAI 연동")
    @PostMapping("/fetch")
    public ApiResponse<String> getFetchNewsApi() {
        int count = this.newsService.fetchAndSaveNews(null);
        return ApiResponse.success(count + "건 저장되었습니다.");
    }

    @Operation(summary = "특정기간 news 정보 fetch", description = "NewsApi, OpenAI 연동")
    @PostMapping("/fetch/period")
    public ApiResponse<String> getFetchNewsApiByPeriod(@RequestBody NewsApiRequest newsApiRequest) {
        int count = this.newsService.fetchAndSaveNews(newsApiRequest);
        return ApiResponse.success(count + "건 저장되었습니다.");
    }

}