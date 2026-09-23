package com.example.springai.config;

import com.example.springai.advisor.MySimpleLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfiguration {

    public ChatMemory chatMemory(){
        return MessageWindowChatMemory.builder()
                .maxMessages(10) // 设置消息窗口大小为10
                .chatMemoryRepository(new InMemoryChatMemoryRepository()) // 内存存储，生产级用redis
                .build();
    }


    @Bean
    public ChatClient chatClient(OpenAiChatModel model, ChatMemory chatMemory){
        return ChatClient
                .builder(model)
                .defaultAdvisors(new MySimpleLoggerAdvisor())
                // .defaultAdvisors(
                //         new SimpleLoggerAdvisor(),
                //         MessageChatMemoryAdvisor.builder(chatMemory).build() // 添加一个聊天记录拦截器
                // )
                .build(); //构建ChatClient对象
    }

    @Bean
    public ChatClient chatClient2(OllamaChatModel model){
        return ChatClient
                .builder(model)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @Bean
    public ChatClient chatClient3(OllamaChatModel model){
        String systemPrompt = "你是一个资深的Java技术顾问。" +
                "禁止回答任何非技术类问题，例如天气或娱乐八卦。" +
                "代码示例必须符合Java21规范。" +
                "回答需要符合以下格式：首先一句话概括问题的核心，然后提供代码示例，最后补充注意事项。" +
                "如果自己不确定，可以说“关于这个问题，我目前没有确切的信息”。禁止编造内容。";
        return ChatClient
                .builder(model)
                .defaultSystem(systemPrompt)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }
}
