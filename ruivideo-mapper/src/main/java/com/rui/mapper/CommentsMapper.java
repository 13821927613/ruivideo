package com.rui.mapper;

import com.rui.pojo.Comments;
import com.rui.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("commentsMapper")
public interface CommentsMapper extends MyMapper<Comments> {
}