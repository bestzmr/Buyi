package com.bestzmr.buyi.system.entity;

import com.bestzmr.buyi.system.representation.UserRepresentation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Merlin
 *
 * @time: 2021/7/23 15:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends AbstractAuditBase{
    private Long id;
    private String userName;
    private String fullName;
    private String password;
    private Boolean enabled;

    /**
     * @JsonIgnore
     * 在json序列化时将pojo中的一些属性忽略掉，标记在属性或者方法上，返回的json数据即不包含该属性
     */
    @JsonIgnore
    private List<UserRole> userRoles = new ArrayList<>();

    public List<SimpleGrantedAuthority> getRoles() {
        List<Role> roles = userRoles.stream().map(UserRole::getRole).collect(Collectors.toList());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorities;
    }

    public UserRepresentation toUserRepresentation() {
        return UserRepresentation.builder().fullName(this.fullName)
                .userName(this.userName).build();
    }
}
