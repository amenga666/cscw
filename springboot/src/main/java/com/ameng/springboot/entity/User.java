package com.ameng.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@TableName("user") //与数据库表对应
@Data //lombok自动生成get、set方法注解
public class User {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    private String userName;
    @JsonIgnore   // 忽略字段，不展示给前端
    private String password;
    private String nickname;
    private String sex;
    private Integer age;
    private String address;
    private Integer role;
}
