package com.bestzmr.buyi.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: Merlin
 * @time: 2021/7/21 16:14
 */
@Data
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
    private Boolean rememberMe;
}
