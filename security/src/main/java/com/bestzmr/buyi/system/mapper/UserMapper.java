package com.bestzmr.buyi.system.mapper;

import com.bestzmr.buyi.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author: Merlin
 * @time: 2021/7/28 9:32
 */
@Mapper
@Component
public interface UserMapper {
    Optional<User> findByUserName(String username);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByUserName(String userName);

    Boolean existsByUserName(String userName);

    Long saveUser(User user);

    List<User> findAllUsers();
}
