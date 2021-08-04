package com.bestzmr.buyi.system.enums;


import lombok.Getter;

/**
 * 角色类型
 */
@Getter
public enum RoleType {
    /**
     * 用户、临时用户、管理者、Admin
     */
    USER("USER", "用户"),
    TEMP_USER("TEMP_USER", "临时用户"),
    MANAGER("MANAGER", "管理者"),
    ADMIN("ADMIN", "Admin");
    private final String name;
    private final String description;

    RoleType(java.lang.String name, java.lang.String description) {
        this.name = name;
        this.description = description;
    }
}
