package com.ameng.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("enroll")   //与数据库表对应
@Data   //lombok自动生成get、set方法注解
public class Enroll {
    @TableId(value = "enroll_id", type = IdType.AUTO)
    private Integer enrollId;
    private String entriesname;
    private String competitor;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date enrolltime;
    private String url;
    private Integer score;
}
