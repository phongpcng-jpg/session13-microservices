package io.edu.rikkei.identity_service.models.dtos.requests;

public record RegisterReq(
        String fullName,
        String username,
        String password
) {
}
