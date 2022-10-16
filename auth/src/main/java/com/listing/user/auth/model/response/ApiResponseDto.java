package com.listing.user.auth.model.response;

import lombok.Data;

@Data
public class ApiResponseDto extends ApiErrorResponse{
    private String responseCode;
    private String responseMessage;
    private String data;
}
