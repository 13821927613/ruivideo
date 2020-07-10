package com.rui.controller;

import com.rui.pojo.Videos;
import com.rui.service.VideoService;
import com.rui.utils.JsonResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program: ruivideo
 * @description: 视频相关接口
 * @author: huangrui
 * @create: 2020-07-09 14:52
 **/

@RestController
@Api(value = "视频相关的接口", tags = {"视频相关的controller"})
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "用户上传视频", notes = "用户上传视频的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", required=true, dataType="String", paramType="query"),
            @ApiImplicitParam(name="bgmId", value="背景音乐id", required=false, dataType="String", paramType="query"),
            @ApiImplicitParam(name="videoSeconds", value="视频时长", required=true, dataType="double", paramType="query"),
            @ApiImplicitParam(name="desc", value="视频描述", required=false, dataType="String", paramType="query"),
            @ApiImplicitParam(name="videoHigh", value="视频高度", required=true, dataType="int", paramType="query"),
            @ApiImplicitParam(name="videoWeight", value="视频宽度", required=true, dataType="int", paramType="query")
    })
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public JsonResult upload(String userId, String bgmId, double videoSeconds, String desc,
                                    int videoHigh, int videoWeight,
                             @ApiParam(value = "短视频", required = true) MultipartFile file) throws IOException {

        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("用户id不能为空");
        }

        //文件保存的命名空间
        final String fileNameSpace = "D:/productivity/Source/Repos/WeChat/ruivideo_dev/users/";
        //保存到数据库中的相对路径
        String videoPathDb = "/users/" + userId + "/videos/";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            //保存文件
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (!StringUtils.isBlank(fileName)) {
                    //文件上传的最终保存路径
                    String finalPath = fileNameSpace + videoPathDb + fileName;
                    //设置数据库的保存路径
                    videoPathDb += fileName;

                    File outFile =new File(finalPath);
                    if (!outFile.getParentFile().exists()) {
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                } else {
                    return JsonResult.errorMsg("上传出错");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.errorMsg("上传出错");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        Videos video = new Videos();
        video.setUserId(userId);
        video.setAudioId(bgmId);
        video.setVideoSeconds((float) videoSeconds);
        video.setVideoWidth(videoWeight);
        video.setVideoHeight(videoHigh);
        video.setVideoDesc(desc);
        video.setVideoPath(videoPathDb);
        video.setLikeCounts((long) 0);
        video.setStatus(1);
        //videoService.saveVideo(video);

        return JsonResult.ok(videoPathDb);
    }
}
