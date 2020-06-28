package com.rui.mapper;

import java.util.List;

import com.rui.pojo.SearchRecords;
import com.rui.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component("searchRecordsMapper")
public interface SearchRecordsMapper extends MyMapper<SearchRecords> {

}