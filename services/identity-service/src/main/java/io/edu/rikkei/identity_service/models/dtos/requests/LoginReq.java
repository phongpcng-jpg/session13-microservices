package io.edu.rikkei.identity_service.models.dtos.requests;

public record LoginReq(
        String username,
        String password
) {
}
