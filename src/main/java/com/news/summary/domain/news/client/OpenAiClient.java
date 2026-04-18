package com.news.summary.domain.news.client;

import com.news.summary.common.client.ApiClient;
import com.news.summary.common.exception.BusinessException;
import com.news.summary.common.exception.ErrorCode;
import com.news.summary.domain.news.dto.OpenAiRequest;
import com.news.summary.domain.news.dto.OpenAiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenAiClient {

    private final ApiClient apiClient;

    public String summarize(String title, String url) {
        String prompt = """
            아래 경제 뉴스 제목을 보고 한국어로 5줄내로 요약해줘.
            핵심 내용, 시장 영향, 투자자 관점에서 간결하게 작성해.
            
            제목: %s
            출처: %s
            """.formatted(title, url);

        OpenAiRequest request = OpenAiRequest.of(prompt);

        OpenAiResponse response = apiClient.postToOpenAi(
                "/chat/completions",
                request,
                OpenAiResponse.class
        );

        if (response == null || response.getContent() == null) {
            throw new BusinessException(ErrorCode.EXTERNAL_API_ERROR);
        }

        return response.getContent();
    }
}