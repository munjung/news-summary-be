package com.news.summary.domain.news.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OpenAiRequest {

    private String model;
    private List<Message> messages;
    @JsonProperty("max_tokens")
    private int maxTokens;

    @Getter
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }

    public static OpenAiRequest of(String prompt) {
        return new OpenAiRequest(
                "gpt-4o-mini",
                List.of(new Message("user", prompt)),
                300
        );
    }
}
