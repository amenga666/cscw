package com.ameng.springboot.controller;

import com.ameng.springboot.common.Result;
import com.ameng.springboot.entity.User;
import com.ameng.springboot.mapper.UserMapper;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 定义为返回JSON的Controller
@RequestMapping("/user") //路由
public class UserController {

    @Resource // 将Mapper引入到Controller中，规范应加上Service
    UserMapper userMapper;

    @PatchMapping // POST接口
    public Result<?> save(@RequestBody User user) {
        userMapper.insert(user); // 新增user实体
        return Result.success();
    }
}
