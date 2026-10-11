package com.ctorres.pokequiz.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse<T> {
    @JsonProperty("response")
    private final Response response;

    @JsonProperty("data")
    private final T data;

    private ApiResponse(Response response, T data) {
        this.response = response;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(Response.builder()
                .code(200)
                .message("OK")
                .build(), data);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<T>(Response.builder()
                .code(code)
                .message(message)
                .build(), null);
    }

    public Response getResponse() {
        return response;
    }

    public T getData() {
        return data;
    }
}
