package dev.kush.authorizationserver.models;

public record ResponseDto(int status, Object data, String message) {
}
