package com.rui.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: ruivideo
 * @description:
 * @author: huangrui
 * @create: 2020-07-22 10:39
 **/

public class FFMpegTest {

    private String ffmpeg;

    public FFMpegTest(String ffmpeg) {
        this.ffmpeg = ffmpeg;
    }

    public void convert(String videoInputPath, String videoOutputPath) throws Exception {
//		ffmpeg -i input.mp4 -y output.avi
        List<String> command = new ArrayList<>();
        command.add(ffmpeg);
        command.add("-i");
        command.add(videoInputPath);
        command.add("-y");
        command.add(videoOutputPath);

        for (String c : command) {
            System.out.print(c + " ");
        }

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process =builder.start();

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
        FFMpegTest ffMpegTest = new FFMpegTest("D:\\productivity\\ffmpg\\ffmpeg-20200628-4cfcfb3-win64-static\\bin\\ffmpeg.exe");

        try {
            ffMpegTest.convert("D:\\F.M.L\\视频\\小程序实战\\视频\\testvideo.mp4",
                    "D:\\F.M.L\\视频\\小程序实战\\视频\\testvideoconvert.avi");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
