package com.listing.user.auth.model.response;

import lombok.Data;

@Data
public class ApiErrorResponse {

    private String errorCode;
    private String errorMessage;
}
