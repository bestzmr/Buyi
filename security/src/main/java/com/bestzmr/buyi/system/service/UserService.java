package com.bestzmr.buyi.system.service;

import com.bestzmr.buyi.system.entity.Role;
import com.bestzmr.buyi.system.entity.User;
import com.bestzmr.buyi.system.enums.RoleType;
import com.bestzmr.buyi.system.exception.RoleNotFoundException;
import com.bestzmr.buyi.system.exception.UserNameAlreadyExistException;
import com.bestzmr.buyi.system.exception.UserNameNotFoundException;
import com.bestzmr.buyi.system.mapper.RoleMapper;
import com.bestzmr.buyi.system.mapper.UserMapper;
import com.bestzmr.buyi.system.mapper.UserRoleMapper;
import com.bestzmr.buyi.system.request.UserRegisterRequest;
import com.bestzmr.buyi.system.request.UserUpdateRequest;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author: Merlin
 * @time: 2021/7/28 9:24
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    public static final String USERNAME = "username:";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;


    public User findUser(String username) {
        return userMapper.findByUserName(username).orElseThrow(() -> new UserNameNotFoundException(ImmutableMap.of(USERNAME, username)));
    }




    @Transactional(rollbackFor = Exception.class)
    public void save(UserRegisterRequest userRegisterRequest) {
        ensureUserNameNotExist(userRegisterRequest.getUserName());
        User user = userRegisterRequest.toUser();
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterRequest.getPassword()));
        Long userId = userMapper.saveUser(user);
        //给用户绑定两个角色：用户和管理者
        Role studentRole = roleMapper.findByName(RoleType.USER.getName()).orElseThrow(() -> new RoleNotFoundException(ImmutableMap.of("roleName", RoleType.USER.getName())));
        Role managerRole = roleMapper.findByName(RoleType.MANAGER.getName()).orElseThrow(() -> new RoleNotFoundException(ImmutableMap.of("roleName", RoleType.MANAGER.getName())));
        userRoleMapper.saveUserRole(userId,studentRole.getId());
        userRoleMapper.saveUserRole(userId,managerRole.getId());
    }


    public void update(UserUpdateRequest userUpdateRequest) {
        User user = findUser(userUpdateRequest.getUserName());
        if (Objects.nonNull(userUpdateRequest.getFullName())) {
            user.setFullName(userUpdateRequest.getFullName());
        }
        if (Objects.nonNull(userUpdateRequest.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(userUpdateRequest.getPassword()));
        }
        if (Objects.nonNull(userUpdateRequest.getEnabled())) {
            user.setEnabled(userUpdateRequest.getEnabled());
        }
        userMapper.saveUser(user);
    }


    public void delete(String userName) {
        if (!userMapper.existsByUserName(userName)) {
            throw new UserNameNotFoundException(ImmutableMap.of(USERNAME, userName));
        }
        userMapper.deleteByUserName(userName);
    }

    public List<User> getAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.findAllUsers();
    }

    public boolean check(String currentPassword, String password) {
        return this.bCryptPasswordEncoder.matches(currentPassword, password);
    }

    private void ensureUserNameNotExist(String userName) {
        boolean exist = userMapper.findByUserName(userName).isPresent();
        if (exist) {
            throw new UserNameAlreadyExistException(ImmutableMap.of(USERNAME, userName));
        }
    }

}
