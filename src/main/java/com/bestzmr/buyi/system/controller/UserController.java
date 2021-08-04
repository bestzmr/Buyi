package com.bestzmr.buyi.system.controller;

import com.bestzmr.buyi.system.entity.User;
import com.bestzmr.buyi.system.representation.UserRepresentation;
import com.bestzmr.buyi.system.request.UserRegisterRequest;
import com.bestzmr.buyi.system.request.UserUpdateRequest;
import com.bestzmr.buyi.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/users")
@Api(tags = "用户")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    @ApiOperation("用户注册")
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        userService.save(userRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @ApiOperation("获取所有用户的信息（分页）")
    public ResponseEntity<List<User>> getAllUser(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        List<User> allUser = userService.getAll(pageNum, pageSize);
        return ResponseEntity.ok().body(allUser);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ApiOperation("更新用户")
    public ResponseEntity<Void> update(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        userService.update(userUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ApiOperation("根据用户名删除用户")
    public ResponseEntity<Void> deleteUserByUserName(@RequestParam("username") String username) {
        userService.delete(username);
        return ResponseEntity.ok().build();
    }
}
