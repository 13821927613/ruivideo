package com.rui.service.impl;

import com.rui.mapper.BgmMapper;
import com.rui.pojo.Bgm;
import com.rui.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Bgm queryBgmById(String bgmId) {
        Example bgmExample = new Example(Bgm.class);
        Example.Criteria criteria = bgmExample.createCriteria();
        criteria.andEqualTo("id", bgmId);

        return bgmMapper.selectOneByExample(bgmExample);
    }
}
