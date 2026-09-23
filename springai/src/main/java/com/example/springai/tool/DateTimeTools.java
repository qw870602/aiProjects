package com.example.springai.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeTools {

    @Tool(description = "获取用户在指定时区的当前日期和时间，用于回答需要实时时间的问题")
    public String getCurrentTime() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    @Tool(description = "设置闹钟，调用此工具可在指定时间触发提醒，时间参数必须是 ISO-8601 格式，例如：2026-05-03 15:30:00")
    public void setAlarm(@ToolParam(description = "闹钟的触发时间，标准格式：yyyy-MM-dd HH:mm:ss") String alarmTime) {
        System.out.println("闹钟已设置，将在 " + alarmTime + " 提醒用户");
        // 可以入库、发送通知什么的
    }

}
