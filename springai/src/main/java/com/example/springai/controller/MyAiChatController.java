package com.example.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class MyAiChatController {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatClient chatClient2;

    @RequestMapping("/ai")
    public String ai(String question){
        return chatClient.prompt().user(question).call().content();
    }

    @RequestMapping("/ai2")
    public String ai2(String question){
        return chatClient2.prompt().user(question).call().content();
    }

    @RequestMapping(value = "/ai3", produces = "text/html;charset=utf-8")
    public Flux<String> ai3(String question){
        return chatClient2.prompt().user(question).stream().content();
    }

}
