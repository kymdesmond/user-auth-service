package com.listing.user.auth.model.response;

import lombok.Data;

@Data
public class ApiResponse extends ApiErrorResponse{
    private String responseCode;
    private String responseMessage;
    private Object data;
}
