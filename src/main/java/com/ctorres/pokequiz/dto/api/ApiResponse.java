package com.ctorres.pokequiz.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse<T> {
    @JsonProperty("response")
    private Response response;

    @JsonProperty("data")
    private T data;

    private ApiResponse() {}

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        Response response = Response.builder()
                        .code(200)
                        .message("OK")
                        .build();
        
        apiResponse.setResponse(response);
        apiResponse.setData(data);
        return apiResponse;
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        Response response = Response.builder()
                        .code(code)
                        .message(message)
                        .build();
        
        apiResponse.setResponse(response);
        apiResponse.setData(null);
        return apiResponse;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
