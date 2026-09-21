package com.example.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyAiChatController {

    @Autowired
    private ChatClient chatClient;

    @RequestMapping("/ai")
    public String ai(String question){
        return chatClient.prompt().user(question).call().content();
    }

}
