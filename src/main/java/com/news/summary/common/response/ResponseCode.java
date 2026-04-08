package com.news.summary.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    // 성공
    SUCCESS("200", "요청이 정상적으로 처리되었습니다."),

    // 클라이언트 오류
    BAD_REQUEST("400", "잘못된 요청입니다."),
    NOT_FOUND("404", "데이터를 찾을 수 없습니다."),

    // 서버 오류
    INTERNAL_SERVER_ERROR("500", "서버 오류가 발생했습니다.");

    private final String code;
    private final String message;
}
