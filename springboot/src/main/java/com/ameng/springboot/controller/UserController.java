package com.ameng.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.ameng.springboot.common.Result;
import com.ameng.springboot.entity.User;
import com.ameng.springboot.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController // 定义为返回JSON的Controller
@RequestMapping("/user") //路由
public class UserController {

    @Resource // 将Mapper引入到Controller中，规范应加上Service
    UserMapper userMapper;

    // 增
    @PostMapping // POST接口
    public Result<?> save(@RequestBody User user) {
        userMapper.insert(user); // 新增user实体
        return Result.success();
    }

    // 删
    @DeleteMapping("/{user_id}") // 占位符 注解中字段名下划线不用改成驼峰
    public Result<?> delete(@PathVariable Long user_id) {
        userMapper.deleteById(user_id);
        return Result.success();
    }

    // 改
    @PutMapping // PUT接口
    public Result<?> update(@RequestBody User user) {
        userMapper.updateById(user);
        return Result.success();
    }

    // 查
    @GetMapping // GET接口
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum, // 不写三个参数会报400，参数可在url中手动添加
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {   // 默认值为空避免空查询报错
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();

        // 值为null无法like
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(User::getUserName, search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUserName, user.getUserName())
                .eq(User::getPassword, user.getPassword()));
        if (res == null) {
            return Result.error(-1, "用户名或密码错误");
        }
        return Result.success(res);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName()));
        if (res != null) {
            return Result.error(-1, "用户名已存在");
        }
        if (user.getPassword() == null) { // 注册界面已经验证密码不能为空，意外情况密码为空时，此处设置默认密码为123456
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }

    @GetMapping("/{user_id}")
    public Result<?> getById(@PathVariable Long user_id) {
        return Result.success(userMapper.selectById(user_id));
    }
}
