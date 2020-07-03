package com.rui.controller;

import com.rui.pojo.Users;
import com.rui.service.UserService;
import com.rui.utils.JsonResult;
import io.swagger.annotations.Api;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @program: ruivideo
 * @description: 用户相关业务的接口
 * @author: huangrui
 * @create: 2020-07-03 15:47
 **/

@RestController
@Api(value = "用户相关业务的接口", tags = {"用户相关业务的controller"})
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    public JsonResult uploadFace(String userId, @RequestParam("file") MultipartFile[] files) throws IOException {

        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("用户id不能为空");
        }

        //文件保存的命名空间
        final String fileNameSpace = "D:/productivity/Source/Repos/WeChat/ruivideo_dev";
        //保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/face/";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (files != null && files.length > 0) {
                String fileName = files[0].getOriginalFilename();
                if (!StringUtils.isBlank(fileName)) {
                    //文件上传的最终保存路径
                    String finalPath = fileNameSpace + uploadPathDB + fileName;
                    //设置数据库的保存路径
                    uploadPathDB += fileName;

                    File outFile =new File(finalPath);
                    if (!outFile.getParentFile().exists()) {
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
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

        Users user = new Users();
        user.setId(userId);
        user.setFaceImage(uploadPathDB);
        userService.updateUserInfo(user);

        return JsonResult.ok(uploadPathDB);
    }
}
