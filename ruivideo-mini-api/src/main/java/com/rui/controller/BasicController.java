package com.rui.controller;

import com.rui.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: ruivideo
 * @description:
 * @author: huangrui
 * @create: 2020-06-28 15:38
 **/

@RestController
public class BasicController {

    @Autowired
    public RedisOperator redis;

    public static final String USER_REDIS_SESSION = "user-redis-session";

    public static final String FINAL_NAME_SPACE = "D:/productivity/Source/Repos/WeChat/ruivideo_dev";

    public static final String FFMPEG_EXE = "D:\\productivity\\ffmpg\\ffmpeg-20200628-4cfcfb3-win64-static\\bin\\ffmpeg.exe";

}
