package io.edu.rikkei.identity_service.models.services;

import io.edu.rikkei.identity_service.models.dtos.requests.LoginReq;
import io.edu.rikkei.identity_service.models.dtos.requests.RegisterReq;
import io.edu.rikkei.identity_service.models.dtos.responses.JwtRes;

public interface AuthService {

    void register(RegisterReq req);

    JwtRes login(LoginReq req);

}
