package com.cloud.spring.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_role")
public class RoleBean {

    private int roleId;

    private String roleName;

    private String roleDescription;

    private String roleCreateTime;
}
