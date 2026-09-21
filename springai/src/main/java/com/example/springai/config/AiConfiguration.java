package com.example.springai.config;

import com.example.springai.advisor.MySimpleLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfiguration {

    @Bean
    public ChatClient chatClient(OpenAiChatModel model){
        return ChatClient
                .builder(model)
                .defaultAdvisors(new MySimpleLoggerAdvisor())
                .build();
    }

    @Bean
    public ChatClient chatClient2(OllamaChatModel model){
        return ChatClient
                .builder(model)
                .build();
    }
}
