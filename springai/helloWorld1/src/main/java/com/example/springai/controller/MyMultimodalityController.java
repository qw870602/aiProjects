package com.example.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyMultimodalityController {

    @Autowired
    private ChatClient chatClient;

    @GetMapping("/analyze-image")
    public String analyzeImage() {
        // 流畅式API构建多模态请求
        String result = chatClient.prompt()
                .user(u -> u
                        .text("请详细描述这张图片的内容，包括物体、颜色、场景和可能的用途")
                        .media(Media.Format.IMAGE_PNG, new ClassPathResource("apple.png"))
                )
                .call()
                .content(); // 直接获取响应内容

        System.out.println(result);
        return "OK";
    }
}
