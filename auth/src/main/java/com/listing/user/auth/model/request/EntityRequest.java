package com.listing.user.auth.model.request;

public record EntityRequest(Integer entityTypeId, String name, String msisdn, String address, Integer createdBy) {}
