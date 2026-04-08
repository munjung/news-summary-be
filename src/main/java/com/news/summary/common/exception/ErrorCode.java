package com.news.summary.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NEWS_NOT_FOUND("N001", "뉴스를 찾을 수 없습니다."),
    KEYWORD_ALREADY_EXISTS("K001", "이미 등록된 키워드입니다."),
    EXTERNAL_API_ERROR("E001", "외부 API 호출에 실패했습니다.");

    private final String code;
    private final String message;
}