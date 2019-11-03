package com.cloud.spring.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.spring.demo.entity.UserBean;

public interface UserService extends IService<UserBean> {
    IPage<UserBean> getAllUsers(int pageNum, int pageSize, String userName);

    UserBean getUserById(int userId);

    int addUser(UserBean userBean);

    int deleteUser(int userId);

    int updateUserInfo(UserBean userBean);

    String selectPasswordByUserName(String userName);
}
