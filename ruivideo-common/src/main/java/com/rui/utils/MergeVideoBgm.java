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
 * @description: 合并视频和背景音乐
 * @author: huangrui
 * @create: 2020-07-22 11:31
 **/

public class MergeVideoBgm {

    private String ffmpegEXE;

    //private Logger log = LoggerFactory.getLogger(MergeVideoBgm.class);

    public MergeVideoBgm(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }

    public void convert(String videoInputPath, String bgmInputPath, double time, String outputPath) throws IOException {
//  ffmpeg -i video.mp4 -i audio.wav -c:v copy -c:a aac -strict experimental -map 0:v:0 -map 1:a:0 output.mp4
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);
        command.add("-i");
        command.add(videoInputPath);
        command.add("-i");
        command.add(bgmInputPath);
        command.add("-t");
        command.add(String.valueOf(time));
        command.add("-c:v");
        command.add("copy");
        command.add("-c:a");
        command.add("aac");
        command.add("-strict");
        command.add("experimental");
        command.add("-map");
        command.add("0:v:0");
        command.add("-map");
        command.add("1:a:0");
        command.add("-y");
        command.add(outputPath);

        StringBuilder cmd = new StringBuilder();
        for (String c : command) {
            cmd.append(c).append(" ");
        }
        System.out.print(cmd.toString());

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
        MergeVideoBgm mergeVideoBgm = new MergeVideoBgm("D:\\productivity\\ffmpg\\ffmpeg-20200628-4cfcfb3-win64-static\\bin\\ffmpeg.exe");
        String videoInputPath = "D:\\productivity\\ffmpg\\ffmpeg-20200628-4cfcfb3-win64-static\\bin\\testvideo.mp4";
        String bgmInputPath = "D:\\productivity\\ffmpg\\ffmpeg-20200628-4cfcfb3-win64-static\\bin\\bgm.flac";
        double time = 11.14;
        String outputPaht = "D:\\productivity\\ffmpg\\ffmpeg-20200628-4cfcfb3-win64-static\\bin\\output.mp4";
        try {
            mergeVideoBgm.convert(videoInputPath, bgmInputPath, time, outputPaht);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
