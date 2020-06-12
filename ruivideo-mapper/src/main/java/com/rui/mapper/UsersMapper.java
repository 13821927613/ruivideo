package com.rui.mapper;

import com.rui.pojo.Users;
import com.rui.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("userMapper")
public interface UsersMapper extends MyMapper<Users> {

}