package com.bestzmr.buyi.system.mapper;

import com.bestzmr.buyi.system.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: Merlin
 * @time: 2021/7/29 14:47
 */
@Mapper
@Component
public interface UserRoleMapper {
    Boolean saveUserRole(Long userId,Long roleId);

    Optional<UserRole> findUserRoleByUserId(String userId);
}
