package com.rui.mapper;

import com.rui.pojo.Videos;
import com.rui.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("videosMapper")
public interface VideosMapper extends MyMapper<Videos> {
}