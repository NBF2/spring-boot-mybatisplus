package com.cloud.spring.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.spring.demo.constant.ResultConstant;
import com.cloud.spring.demo.entity.UserBean;
import com.cloud.spring.demo.service.UserService;
import com.cloud.spring.demo.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/users")
@Slf4j
@Api("用户管理控制层")
public class UserController {

    private UserService userService;

    /**
     * 分页获取用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/getUsersPage")
    @ApiOperation("分页获取用户列表")
    public JSONObject getAllUsers(@RequestParam(value = "pageNum") int pageNum,
                                  @RequestParam(value = "pageSize") int pageSize,
                                  @RequestParam(value = "userName", required = false) String userName) {
        IPage<UserBean> userList = userService.getAllUsers(pageNum, pageSize, userName);
        if (null != userList) {
            return ResultUtil.data(userList);
        } else {
            return ResultUtil.fail("获取用户列表失败");
        }
    }

    /**
     * 根据用户Id获取用户详情
     * @param userId
     * @return
     */
    @GetMapping(value = "/getUserById")
    @ApiOperation("根据用户Id获取用户信息")
    public JSONObject getUserById(@RequestParam(value = "userId") int userId) {
        UserBean userBean = userService.getUserById(userId);
        if (null != userBean) {
            return ResultUtil.data(userBean);
        } else {
            return ResultUtil.fail("根据用户id获取用户详情失败");
        }
    }

    /**
     * 新增用户
     * @param userBean
     * @return
     */
    @PostMapping(value = "/addUser")
    @ApiOperation("新增用户")
    public JSONObject addUser(@RequestBody UserBean userBean) {
        int result = userService.addUser(userBean);
        if (result > 0) {
            return ResultUtil.data("");
        } else if (result == ResultConstant.USER_NAME_EXIST) {
            return ResultUtil.fail("用户名已经存在");
        } else {
            return ResultUtil.fail("新增用户失败");
        }
    }

    /**
     * 更新用户信息
     * @param userBean
     * @return
     */
    @PutMapping(value = "/updateUser")
    @ApiOperation("更新用户信息")
    public JSONObject updateUser(@RequestBody UserBean userBean) {
        int result = userService.updateUserInfo(userBean);
        if (result > 0) {
            return ResultUtil.data("");
        } else {
            return ResultUtil.fail("更新用户信息失败");
        }
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @DeleteMapping("/deleteUserById")
    @ApiOperation("删除用户")
    public JSONObject deleteUser(@RequestParam(value = "userId") int userId) {
        int result = userService.deleteUser(userId);
        if (result > 0) {
            return ResultUtil.data("");
        } else {
            return ResultUtil.fail("删除用户失败");
        }
    }

}
