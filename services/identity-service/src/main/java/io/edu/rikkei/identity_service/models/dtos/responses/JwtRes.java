package io.edu.rikkei.identity_service.models.dtos.responses;

import java.util.List;

public record JwtRes(
        String accessToken,
        String type,
        List<String> roles
) {
}
