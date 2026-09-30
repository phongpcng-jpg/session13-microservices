package io.edu.rikkei.identity_service.models.services.impl;

import lombok.RequiredArgsConstructor;
import io.edu.rikkei.identity_service.exceptions.BadRequestException;
import io.edu.rikkei.identity_service.exceptions.NotFoundException;
import io.edu.rikkei.identity_service.models.constants.RoleName;
import io.edu.rikkei.identity_service.models.dtos.requests.LoginReq;
import io.edu.rikkei.identity_service.models.dtos.requests.RegisterReq;
import io.edu.rikkei.identity_service.models.dtos.responses.JwtRes;
import io.edu.rikkei.identity_service.models.entities.Role;
import io.edu.rikkei.identity_service.models.entities.User;
import io.edu.rikkei.identity_service.models.repositories.RoleRepository;
import io.edu.rikkei.identity_service.models.repositories.UserRepository;
import io.edu.rikkei.identity_service.models.services.AuthService;
import io.edu.rikkei.identity_service.security.jwt.JwtUtils;
import io.edu.rikkei.identity_service.security.principal.MyUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager manager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Override
    public void register(RegisterReq req) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new NotFoundException("Role not found")));

        User user = User.builder()
                .fullName(req.fullName())
                .username(req.username())
                .password(encoder.encode(req.password()))
                .roles(roles)
                .build();

        userRepository.save(user);
    }

    @Override
    public JwtRes login(LoginReq req) {
        Authentication authentication;

        try {
            authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(req.username(),req.password()));
        } catch (AuthenticationException e) {
            throw new BadRequestException("Username or password is incorrect");
        }

//        SecurityContextHolder.getContext().setAuthentication(authentication);
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

        return new JwtRes(
                jwtUtils.generateToken(userDetails.getUser()),
                "Bearer",
                userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        );
    }
}
