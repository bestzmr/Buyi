package com.bestzmr.buyi.security.filter;

import com.bestzmr.buyi.security.utils.JwtTokenUtils;
import io.jsonwebtoken.JwtException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Merlin
 * @time: 2021/7/21 9:41
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final StringRedisTemplate stringRedisTemplate;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, StringRedisTemplate stringRedisTemplate) {
        super(authenticationManager);
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        System.out.println("authentication: " + token);
        if (null == token || !(token.startsWith("Bearer "))) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }

        String tokenValue = token.replace("Bearer ", "");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        try {
            System.out.println("id: " + JwtTokenUtils.getId(tokenValue));
            String previousToken = stringRedisTemplate.opsForValue().get(JwtTokenUtils.getId(tokenValue));
            // 与上一次登录的token作比较，不相同时
            if (!token.equals(previousToken)) {
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }

            // 相同时，已登录，获取权限
            usernamePasswordAuthenticationToken = JwtTokenUtils.getAuthentication(tokenValue);
        } catch (JwtException e) {
            logger.error("Invalid jwt : " + e.getMessage());
        }
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
    }
}
