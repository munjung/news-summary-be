package com.news.summary.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(description = "공통 응답")
public class ApiResponse<T> {

    @Schema(description = "응답 코드", example = "200")
    private final String code;
    @Schema(description = "응답 메시지", example = "성공")
    private String message;
    private T data;

    private ApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage(),
                data
        );
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(
                ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage(),
                null
        );
    }

    // ResponseCode 기반 실패 (공통 에러)
    public static <T> ApiResponse<T> fail(ResponseCode responseCode) {
        return new ApiResponse<>(responseCode.getCode(), responseCode.getMessage(), null);
    }

    // ErrorCode 기반 실패 (비즈니스 에러)
    public static <T> ApiResponse<T> fail(String code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}