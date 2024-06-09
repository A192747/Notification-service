package org.oril.services;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.oril.dto.AuthRequest;
import org.oril.dto.UserDTO;
import org.oril.dto.AuthResponse;
import org.oril.entities.User;
import org.oril.exceptions.UnAuthException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;


    public AuthResponse register(UserDTO request) {
        try {
            request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
            User registeredUser = restTemplate.postForObject("http://user-service/users/register", request, User.class);
            return generateTokens(registeredUser);
        } catch (Exception e) {
            throw new UnAuthException("Не удалось зарегистрировать пользователя с таким именем! Попробуйте другое имя пользователя");
        }
    }

    public AuthResponse login(UserDTO request) {
        try {
            User loginUser = restTemplate.postForObject("http://user-service/users/login", request, User.class);
            return generateTokens(loginUser);
        } catch (Exception e) {
            throw new UnAuthException("Неверный логин или пароль!");
        }
    }

    public AuthResponse refreshTokens(AuthRequest request) {
        String token = request.getRefreshToken();
        if(!jwtUtil.isValidJwtToken(token))
            throw new UnAuthException("Неверный токен!");
        if(jwtUtil.isExpired(token))
            throw new UnAuthException("Необходимо заново войти в аккаунт");

        Claims claims = jwtUtil.getClaims(token);
        return generateTokens(claims);
    }

    public AuthResponse generateTokens(User user) {
        String accessToken = jwtUtil.generate(user.getId(), user.getName(), user.getRole().toString(), "ACCESS");
        String refreshToken = jwtUtil.generate(user.getId(), user.getName(), user.getRole().toString(), "REFRESH");
        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse generateTokens(Claims claims) {
        String accessToken = jwtUtil.generate(Integer.valueOf(claims.get("id").toString()), claims.get("name").toString(), claims.get("role").toString(), "ACCESS");
        String refreshToken = jwtUtil.generate(Integer.valueOf(claims.get("id").toString()), claims.get("name").toString(), claims.get("role").toString(), "REFRESH");
        return new AuthResponse(accessToken, refreshToken);
    }

}
