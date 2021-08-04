package com.bestzmr.buyi.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: Merlin
 * @time: 2021/7/23 17:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole extends AbstractAuditBase implements Serializable {
    private Long id;
    private User user;
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
