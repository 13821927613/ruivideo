package com.rui.mapper;

import com.rui.pojo.UsersLikeVideos;
import com.rui.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("userLikeVideosMapper")
public interface UsersLikeVideosMapper extends MyMapper<UsersLikeVideos> {
}