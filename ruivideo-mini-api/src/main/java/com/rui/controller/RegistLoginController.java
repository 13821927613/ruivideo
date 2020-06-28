package com.rui.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rui.pojo.Users;
import com.rui.service.UserService;
import com.rui.utils.JsonResult;
import com.rui.utils.MD5Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="用户注册登录的接口", tags= {"注册和登录的controller"})
public class RegistLoginController {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value="用户注册", notes="用户注册的接口")
	@PostMapping("/regist")
	public JsonResult regist(@RequestBody Users user) {

		try {
			//判断用户名和密码是否为空
			if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
				return JsonResult.errorMsg("用户名或密码不能为空！");
			}
			//判断用户名是否存在
			boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
			//保存用户注册信息
			if (!usernameIsExist) {
				user.setNickname(user.getUsername());
				user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
				user.setFansCounts(0);
				user.setFansCounts(0);
				user.setReceiveLikeCounts(0);
				userService.saveUser(user);

				user.setPassword("");
				return JsonResult.ok(user);
			} else {
				return JsonResult.errorMsg("用户名已存在！");
			}
		} catch (Exception e){
			return JsonResult.errorMsg(e.getMessage());
		}
	}

	@ApiOperation(value = "用户登陆", notes = "用户登陆的接口")
	@PostMapping("/login")
	public JsonResult login(@RequestBody Users user) {

		try{
			//判断用户名密码是否为空
			if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
				return JsonResult.errorMsg("用户名或密码不能为空");
			}
			//判断用户是否存在
			Users users = userService.queryUsernamePassword(user.getUsername(),
					MD5Utils.getMD5Str(user.getPassword()));
			if (users != null) {
				users.setPassword("");
				return JsonResult.ok(users);
			} else {
				return JsonResult.errorMsg("用户名密码错误");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.errorMsg(e.getMessage());
		}
	}
	
}
