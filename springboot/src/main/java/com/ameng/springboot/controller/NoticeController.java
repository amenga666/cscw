package com.ameng.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.ameng.springboot.common.Result;
import com.ameng.springboot.entity.Notice;
import com.ameng.springboot.mapper.NoticeMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController   // 定义为返回JSON的Controller
@RequestMapping("/notice")   //路由
public class NoticeController {

    @Resource   // 将Mapper引入到Controller中，规范应加上Service
    NoticeMapper noticeMapper;

    // 增
    @PostMapping   // POST接口
    public Result<?> save(@RequestBody Notice notice) {
        notice.setTime(new Date());    // 设置发布时间
        noticeMapper.insert(notice);   // 新增notice实体
        return Result.success();
    }

    // 删
    @DeleteMapping("/{notice_id}")   // 占位符 注解中字段名下划线不用改成驼峰
    public Result<?> delete(@PathVariable Long notice_id) {
        noticeMapper.deleteById(notice_id);
        return Result.success();
    }

    // 改
    @PutMapping   // PUT接口
    public Result<?> update(@RequestBody Notice notice) {
        noticeMapper.updateById(notice);
        return Result.success();
    }

    // 查
    @GetMapping   // GET接口
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,   // 不写三个参数会报400，参数可在url中手动添加
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Notice> wrapper = Wrappers.<Notice>lambdaQuery();

        // 值为null无法like
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Notice::getTitle, search);
        }
        Page<Notice> noticePage = noticeMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(noticePage);
    }
}
