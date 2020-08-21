package com.rui.controller;

import com.rui.enums.VideoStatusEnum;
import com.rui.pojo.Bgm;
import com.rui.pojo.Videos;
import com.rui.service.BgmService;
import com.rui.service.VideoService;
import com.rui.utils.FetchVideoCover;
import com.rui.utils.JsonResult;
import com.rui.utils.MergeVideoBgm;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.tools.shell.IO;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: ruivideo
 * @description: 视频相关接口
 * @author: huangrui
 * @create: 2020-07-09 14:52
 **/

@RestController
@Api(value = "视频相关的接口", tags = {"视频相关的controller"})
@RequestMapping("/video")
public class VideoController extends BasicController{

    @Autowired
    private VideoService videoService;

    @Autowired
    private BgmService bgmService;

    @Autowired
    private Sid sid;

    private Logger log = LoggerFactory.getLogger(VideoController.class);

    @ApiOperation(value = "用户上传视频", notes = "用户上传视频的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", required=true, dataType="String", paramType="form"),
            @ApiImplicitParam(name="bgmId", value="背景音乐id", required=false, dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoSeconds", value="视频时长", required=true, dataType="double", paramType="form"),
            @ApiImplicitParam(name="desc", value="视频描述", required=false, dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoHigh", value="视频高度", required=true, dataType="int", paramType="form"),
            @ApiImplicitParam(name="videoWeight", value="视频宽度", required=true, dataType="int", paramType="form")
    })
    @PostMapping(value = "/uploadVideo", headers = "content-type=multipart/form-data")
    public JsonResult uploadVideo(String userId, String bgmId, double videoSeconds, String desc,
                                    int videoHigh, int videoWeight,
                             @ApiParam(value = "短视频", required = true) MultipartFile file) {

        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("用户id不能为空");
        }

        log.info("user upload video, userId: " + userId);
        //文件保存的命名空间
        //final String fileNameSpace = "D:/productivity/Source/Repos/WeChat/ruivideo_dev";
        //生产视频主键id
        String videoId = sid.nextShort();
        //保存到数据库中的相对路径
        String videoPathDb = "/users/" + userId + "/videos/" + videoId + "/";
        String coverPathDb = "/users/" + userId + "/videos/" + videoId + "/";

        try {
            log.info("upload video");
            boolean uploadSuccess = uploadFile(file, videoPathDb);
            if (!uploadSuccess) {
                return JsonResult.errorMsg("上传视频失败");
            }
            String videoName = file.getOriginalFilename();
            String videoFinalPath = FINAL_NAME_SPACE + videoPathDb + videoName;
            String videoFileName = "";
            String videoFormat = "mp4";
            Pattern p = Pattern.compile("(^.*)\\.(.*$)");
            Matcher m = p.matcher(videoName);
            if (m.find()) {
                videoFileName = m.group(1);
                videoFormat = m.group(2);
            }
            //保存视频截图
            FetchVideoCover fetchVideoCover  = new FetchVideoCover(FFMPEG_EXE);
            fetchVideoCover.fetch(videoFinalPath, FINAL_NAME_SPACE + coverPathDb + "cover.jpg");
            //合并视频音频
            if (!StringUtils.isBlank(bgmId)) {
                Bgm bgm = bgmService.queryBgmById(bgmId);
                if (bgm != null) {
                    String bgmPath = FINAL_NAME_SPACE + bgm.getPath();
                    videoFileName += "_output." + videoFormat;
                    MergeVideoBgm mergeVideoBgm = new MergeVideoBgm(FFMPEG_EXE);
                    mergeVideoBgm.convert(videoFinalPath, bgmPath, videoSeconds,
                            FINAL_NAME_SPACE + videoPathDb + videoFileName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.errorMsg("上传视频失败");
        }

        //保存视频信息
        Videos video = new Videos();
        video.setUserId(userId);
        video.setAudioId(bgmId);
        video.setVideoSeconds((float) videoSeconds);
        video.setVideoWidth(videoWeight);
        video.setVideoHeight(videoHigh);
        video.setVideoDesc(desc);
        video.setVideoPath(videoPathDb);
        video.setCoverPath(coverPathDb);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCreateTime(new Date());
        video.setId(videoId);
        videoService.saveVideo(video);

        return JsonResult.ok(videoId);
    }

    @ApiOperation(value = "用户上传视频封面", notes = "用户上传视频封面的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", required=true, dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoId", value="视频主键id", required=true, dataType="String", paramType="form")
    })
    @PostMapping(value = "/uploadCover", headers = "content-type=multipart/form-data")
    public JsonResult uploadCover(String userId, String videoId,
                                  @ApiParam(value = "短视频", required = true) MultipartFile file) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
            return JsonResult.errorMsg("用户id和视频主键id不能为空");
        }
        log.info("user upload video cover, userId: " + userId);
        //保存到数据库中的相对路径
        String coverPathDb = "/users/" + userId + "/videos/" + videoId + "/";
        try {
            boolean uploadSuccess = uploadFile(file, coverPathDb);
            if (!uploadSuccess) {
                return JsonResult.errorMsg("上传封面失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.errorMsg("上传封面失败");
        }
        videoService.uploadVideoCover(videoId, coverPathDb);

        return JsonResult.ok();
    }


    private boolean uploadFile(MultipartFile file, String pathDb) throws IOException {
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            //保存文件
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (!StringUtils.isBlank(fileName)) {
                    //文件上传的最终保存路径
                    String finalPath = FINAL_NAME_SPACE + pathDb + fileName;
                    //设置数据库的保存路径
                    pathDb += fileName;
                    File outFile = new File(finalPath);
                    if (!outFile.getParentFile().exists()) {
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                    log.info("file upload success, file path: " + finalPath);
                } else {
                    log.error("file upload fail");
                    return false;
                }
            }
        } catch (Exception e) {
            log.error("file upload fail");
            e.printStackTrace();
            return false;
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return true;
    }
}
