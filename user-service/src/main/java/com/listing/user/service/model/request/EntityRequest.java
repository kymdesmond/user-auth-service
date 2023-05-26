package com.listing.user.service.model.request;

public record EntityRequest(Integer entityTypeId, String name, String msisdn,
                            String email, String website, String address, Integer createdBy) {}
