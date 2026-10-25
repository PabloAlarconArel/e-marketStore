package org.marketstore.authservice.dto.response;

public record ApiResponse<T>(int status, String message, T data) {
}
