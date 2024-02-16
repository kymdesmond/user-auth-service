package com.listing.user.service.model.request;

public record ChangePasswordRequest(String currentPassword, String newPassword, String confirmPassword) {
}
