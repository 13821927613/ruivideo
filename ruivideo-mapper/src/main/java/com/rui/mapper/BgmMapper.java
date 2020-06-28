package com.rui.mapper;

import com.rui.pojo.Bgm;
import com.rui.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("bgmMapper")
public interface BgmMapper extends MyMapper<Bgm> {
}