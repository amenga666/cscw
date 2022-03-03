package com.ameng.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.ameng.springboot.common.Result;
import com.ameng.springboot.entity.Enroll;
import com.ameng.springboot.mapper.EnrollMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController   // 定义为返回JSON的Controller
@RequestMapping("/enroll") //路由
public class EnrollController {

    @Resource   // 将Mapper引入到Controller中，规范应加上Service
    EnrollMapper enrollMapper;

    // 增
    @PostMapping   // POST接口
    public Result<?> save(@RequestBody Enroll enroll) {
        enroll.setEnrolltime(new Date());   // 设置发布时间，如果数据库中时间类型选择时间戳就不需要在这里设置
        enrollMapper.insert(enroll);   // 新增enroll实体
        return Result.success();
    }

    // 删
    @DeleteMapping("/{enroll_id}")   // 占位符 注解中字段名下划线不用改成驼峰
    public Result<?> delete(@PathVariable Long enroll_id) {
        enrollMapper.deleteById(enroll_id);
        return Result.success();
    }

    // 改
    @PutMapping   // PUT接口
    public Result<?> update(@RequestBody Enroll enroll) {
        enrollMapper.updateById(enroll);
        return Result.success();
    }

    // 查
    @GetMapping   // GET接口
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,   // 不写三个参数会报400，参数可在url中手动添加
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Enroll> wrapper = Wrappers.<Enroll>lambdaQuery();

        // 值为null无法like
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Enroll::getEntriesname, search);
        }
        Page<Enroll> enrollPage = enrollMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(enrollPage);
    }
}
