package com.rui.mapper;

import com.rui.pojo.UsersReport;
import com.rui.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("usersReportMapper")
public interface UsersReportMapper extends MyMapper<UsersReport> {
}