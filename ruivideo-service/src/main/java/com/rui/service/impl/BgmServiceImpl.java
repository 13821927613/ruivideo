package com.rui.service.impl;

import com.rui.mapper.BgmMapper;
import com.rui.pojo.Bgm;
import com.rui.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: ruivideo
 * @description:
 * @author: huangrui
 * @create: 2020-07-09 10:58
 **/

@Service("bgmService")
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;

    @Override
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }
}
