package com.rui.enums;

/**
 * @program: ruivideo
 * @description: 视频状态枚举类
 * @author: huangrui
 * @create: 2020-08-21 10:08
 **/

public enum VideoStatusEnum {

    SUCCESS(1),
    FORBID(2);

    public final int value;

    VideoStatusEnum(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

}
