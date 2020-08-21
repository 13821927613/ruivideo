package com.rui.service.impl;

import com.rui.mapper.VideosMapper;
import com.rui.pojo.Videos;
import com.rui.service.VideoService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: ruivideo
 * @description:
 * @author: huangrui
 * @create: 2020-07-09 15:10
 **/

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videoMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveVideo(Videos video) {
        videoMapper.insertSelective(video);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void uploadVideoCover(String videoId, String coverPath) {
        Videos video = new Videos();
        video.setId(videoId);
        video.setCoverPath(coverPath);
        videoMapper.updateByPrimaryKeySelective(video);
    }
}
