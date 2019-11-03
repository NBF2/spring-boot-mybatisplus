package com.cloud.spring.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.spring.demo.constant.ResultConstant;
import com.cloud.spring.demo.entity.UserBean;
import com.cloud.spring.demo.mapper.UserMapper;
import com.cloud.spring.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserBean> implements UserService {

    private UserMapper userMapper;

    @Override
    public IPage<UserBean> getAllUsers(int pageNum, int pageSize, String userName) {
        IPage<UserBean> page = new Page<>(pageNum, pageSize);
        try {
            LambdaQueryWrapper<UserBean> wrapper = new LambdaQueryWrapper<>();
            wrapper.like(UserBean::getUserName, userName);
            page = this.baseMapper.selectPage(page, wrapper);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public UserBean getUserById(int userId) {
        return this.baseMapper.selectById(userId);
    }

    @Override
    public int addUser(UserBean userBean) {
        int result = 0;
        //判断是否用户名重复
        LambdaQueryWrapper<UserBean> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserBean::getUserName, userBean.getUserName());
        UserBean user = this.baseMapper.selectOne(wrapper);
        if (null != user) {
            return ResultConstant.USER_NAME_EXIST;
        }
        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        userBean.setUserCreateTime(Timestamp.valueOf(time));

        //密码加密
        Md5Hash md5Hash = new Md5Hash(userBean.getUserPassword());
        userBean.setUserPassword(md5Hash.toString());

        //插入成功后，自动回写主键到实体类
        result = this.baseMapper.insert(userBean);

        return result;
    }

    @Override
    public int deleteUser(int userId) {
        return this.baseMapper.deleteById(userId);
    }

    @Override
    public int updateUserInfo(UserBean userBean) {
        return this.baseMapper.updateById(userBean);
    }

    @Override
    public String selectPasswordByUserName(String userName) {
        LambdaQueryWrapper<UserBean> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserBean::getUserName, userName);
        UserBean userBean = this.baseMapper.selectOne(wrapper);
        if (null != userBean) {
            return userBean.getUserPassword();
        }
        return null;
    }
}
