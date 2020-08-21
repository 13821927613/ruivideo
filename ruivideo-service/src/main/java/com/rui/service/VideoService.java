package com.rui.service;

import com.rui.pojo.Videos;

/**
 * @program: ruivideo
 * @description:
 * @author: huangrui
 * @create: 2020-07-09 15:09
 **/

public interface VideoService {

    /**
     * 保存视频信息
     * @param video
     */
    void saveVideo(Videos video);

    /**
     * 修改视频封面
     * @param videoId
     * @param coverPath
     * @return
     */
    void uploadVideoCover(String videoId, String coverPath);
}
