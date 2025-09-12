package com.ctorres.pokequiz.dto.api;

public class Response {
    private int code;
    private String message;
    
    public Response() {
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        
        private Response response = new Response();

        public Builder code(int code) {
            response.setCode(code);
            return this;
        }

        public Builder message(String message) {
            response.setMessage(message);
            return this;
        }

        public Response build() {
            return response;
        }
    }
}
