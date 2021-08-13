package com.bestzmr.buyi.system.mapper;

import com.bestzmr.buyi.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: Merlin
 * @time: 2021/7/29 14:47
 */
@Mapper
@Component
public interface RoleMapper {
    Optional<Role> findByName(String roleName);

    Optional<Role> findById(String roleId);
}
