package com.rui.service.impl;

import com.rui.mapper.VideosMapper;
import com.rui.pojo.Videos;
import com.rui.service.VideoService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void saveVideo(Videos video) {
        String videoId = sid.nextShort();
        video.setId(videoId);
        videoMapper.insert(video);
    }
}
