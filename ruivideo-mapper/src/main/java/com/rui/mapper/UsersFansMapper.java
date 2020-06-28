package com.rui.mapper;

import com.rui.pojo.UsersFans;
import com.rui.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component("usersFansMapper")
public interface UsersFansMapper extends MyMapper<UsersFans> {
}