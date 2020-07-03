package com.rui.controller;


import com.rui.pojo.vo.UsersVo;
import io.swagger.annotations.ApiImplicitParam;
import net.sf.jsqlparser.expression.UserVariable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

import java.util.UUID;

@RestController
@Api(value="用户注册登录的接口", tags= {"注册和登录的controller"})
public class RegistLoginController extends BasicController{
	
	@Autowired
	private UserService userService;

	private Logger log = LoggerFactory.getLogger(RestController.class);
	
	@ApiOperation(value="用户注册", notes="用户注册的接口")
	@PostMapping("/regist")
	public JsonResult regist(@RequestBody Users user) {

		log.info("user regist");
		try {
			//判断用户名和密码是否为空
			if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
				log.warn("username or password is null");
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
				UsersVo usersVo = setUserRedisSessionToken(user);
				log.info("regist success");
				return JsonResult.ok(usersVo);
			} else {
				log.info("username already exists");
				return JsonResult.errorMsg("用户名已存在！");
			}
		} catch (Exception e){
			return JsonResult.errorMsg(e.getMessage());
		}
	}

	@ApiOperation(value = "用户登陆", notes = "用户登陆的接口")
	@PostMapping("/login")
	public JsonResult login(@RequestBody Users user) {

		log.info("login");
		try{
			//判断用户名密码是否为空
			if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
				log.warn("username or password is null");
				return JsonResult.errorMsg("用户名或密码不能为空");
			}
			//判断用户是否存在
			Users users = userService.queryUsernamePassword(user.getUsername(),
					MD5Utils.getMD5Str(user.getPassword()));
			if (users != null) {
				users.setPassword("");
				UsersVo usersVo = setUserRedisSessionToken(users);
				log.info("user login success");
				return JsonResult.ok(usersVo);
			} else {
				log.warn("wrong username or password ");
				return JsonResult.errorMsg("用户名密码错误");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.errorMsg(e.getMessage());
		}
	}

	@ApiOperation(value = "用户注销", notes = "用户注销的接口")
	@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
	@PostMapping("/logout")
	public JsonResult logout(String userId){

		log.info("logout user " + userId);
		redis.del(USER_REDIS_SESSION + ":" +userId);
		return JsonResult.ok();
	}

	private UsersVo setUserRedisSessionToken(Users userModel) {

		String uniqueToken = UUID.randomUUID().toString();
		redis.set(USER_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, 1000*60*30);

		UsersVo usersVo = new UsersVo();
		BeanUtils.copyProperties(userModel, usersVo);
		usersVo.setUserToken(uniqueToken);
		return usersVo;
	}

}
