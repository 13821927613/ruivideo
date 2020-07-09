package com.rui.controller;

import com.rui.service.BgmService;
import com.rui.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: ruivideo
 * @description:
 * @author: huangrui
 * @create: 2020-07-09 10:59
 **/

@RestController
@Api(value = "背景音乐相关接口", tags = {"背景音乐相关controller"})
@RequestMapping("/bgm")
public class BgmController {

    @Autowired
    private BgmService bgmService;

    @ApiOperation(value = "查询背景音乐列表", notes = "查询背景音乐列表的接口")
    @PostMapping("/list")
    public JsonResult bgmList() {
        return JsonResult.ok(bgmService.queryBgmList());
    }
}
