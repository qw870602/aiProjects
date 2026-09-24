package com.example.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyMcpController {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private SyncMcpToolCallbackProvider mcpToolCallbackProvider;

    @RequestMapping("/ai/mcp")
    public String aiWithMcp() {
        String q = "查一下上海天气";
        String result= chatClient.prompt()
                .system("用户询问天气时，你必须调用工具查询后再用简短中文回答。")
                .user(q).tools(mcpToolCallbackProvider)
                .call()
                .content();
        System.out.println(result);
        return "OK";
    }
}
