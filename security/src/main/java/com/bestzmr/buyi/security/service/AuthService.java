package com.bestzmr.buyi.security.service;


import com.bestzmr.buyi.system.entity.User;
import com.bestzmr.buyi.security.dto.LoginRequest;
import com.bestzmr.buyi.security.entity.JwtUser;
import com.bestzmr.buyi.security.utils.CurrentUserUtils;
import com.bestzmr.buyi.security.utils.JwtTokenUtils;
import com.bestzmr.buyi.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Merlin
 * @time: 2021/7/16 9:45
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {
    private final UserService userService;
    private final StringRedisTemplate stringRedisTemplate;
    private final CurrentUserUtils currentUserUtils;

    public String createToken(LoginRequest loginRequest) {
        User user = userService.findUser(loginRequest.getUsername());
        if (!userService.check(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("The user name or password is not correct.");
        }
        JwtUser jwtUser = new JwtUser(user);
        if (!jwtUser.isEnabled()) {
            throw new BadCredentialsException("User is forbidden to login");
        }
        List<String> authorities = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String token = JwtTokenUtils.createToken(user.getUserName(), user.getId().toString(), authorities, loginRequest.getRememberMe());
        stringRedisTemplate.opsForValue().set(user.getId().toString(), token);
        return token;
    }

    public void removeToken() {
        stringRedisTemplate.delete(currentUserUtils.getCurrentUser().getId().toString());
    }
}