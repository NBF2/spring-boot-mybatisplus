package com.cloud.spring.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Data
@ApiModel("用户信息实体类")
@TableName("sys_user")
public class UserBean {

    //主键类型  AUTO:"数据库ID自增", INPUT:"用户输入ID",ID_WORKER:"全局唯一ID (数字类型唯一ID)", UUID:"全局唯一ID UUID";
    //id-type的配置写在配置文件中时，通过id未能查询到用户信息，原因不明
    @ApiModelProperty("用户Id")
    @TableId(type = IdType.AUTO)
    private int userId;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("用户密码")
    private String userPassword;

    @ApiModelProperty("用户创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp userCreateTime;

    @ApiModelProperty("用户所属角色id")
    private int roleId;

    @ApiModelProperty("用户所属角色名称")
    @TableField(exist = false)
    private String roleName;
}
