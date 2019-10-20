package com.cloud.spring.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.spring.demo.entity.UserBean;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<UserBean> {

}
