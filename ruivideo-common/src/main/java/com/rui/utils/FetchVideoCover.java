package com.rui.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: ruivideo
 * @description: 获取视频截图
 * @author: huangrui
 * @create: 2020-08-21 14:39
 **/

public class FetchVideoCover {

    private Logger log = LoggerFactory.getLogger(FetchVideoCover.class);

    private String ffmpegEXE;

    public FetchVideoCover(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }

    public void fetch(String videoInputPath, String outputPath) throws IOException {
        //ffmpeg.exe -ss 00:00:01 -y -i testvideo.mp4 -vframes 1 new.jpg
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);
        command.add("-ss");
        command.add("00:00:01");
        command.add("-y");
        command.add("-i");
        command.add(videoInputPath);
        command.add("-vframes");
        command.add("1");
        command.add(outputPath);

        StringBuilder cmd = new StringBuilder();
        for (String c : command) {
            cmd.append(c).append(" ");
        }
        //System.out.print(cmd.toString());
        log.info(cmd.toString());

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }

    public static void main(String[] args) {
        FetchVideoCover fetchVideoCover  = new FetchVideoCover("D:\\productivity\\ffmpg\\ffmpeg-20200628-4cfcfb3-win64-static\\bin\\ffmpeg.exe");
        String videoInputPath = "D:\\productivity\\ffmpg\\ffmpeg-20200628-4cfcfb3-win64-static\\bin\\testvideo.mp4";
        String outputPath = "D:\\productivity\\ffmpg\\ffmpeg-20200628-4cfcfb3-win64-static\\bin\\cover.jpg";
        try {
            fetchVideoCover.fetch(videoInputPath, outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
