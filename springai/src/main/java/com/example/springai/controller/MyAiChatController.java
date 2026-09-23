package com.example.springai.controller;

import com.example.springai.tool.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
public class MyAiChatController {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatClient chatClient2;

    @Autowired
    private ChatClient chatClient3;

    @Autowired
    private DateTimeTools dateTimeTools;

    @RequestMapping("/ai")
    public String ai(String question) {
        return chatClient2.prompt().user(question).call().content();
    }

    @RequestMapping("/aitool")
    public String aitool(String question) {
        return chatClient.prompt()
                .user(question)  //设置用户输入
                .tools(dateTimeTools) //注册工具
                .call()
                .content();
    }

    @RequestMapping("/aiPlus")
    public String aiPlus(String question, String convId) {
        return chatClient.prompt()
                .user(question)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, convId))
                .call()
                .content();
    }

    @RequestMapping("/ai2")
    public String ai2(String question) {
        return chatClient2.prompt().user(question).call().content();
    }

    @RequestMapping(value = "/ai3", produces = "text/html;charset=utf-8")
    public Flux<String> ai3(String question) {
        return chatClient2.prompt().user(question).stream().content();
    }

    @RequestMapping(value = "/ask", produces = "text/html;charset=utf-8")
    public Flux<String> ask(String topic) {
        PromptTemplate template = new PromptTemplate("介绍下{topic}");
        Prompt prompt = template.create(Map.of("topic", topic));
        return chatClient.prompt(prompt).stream().content();
    }

    @RequestMapping(value = "/ask2", produces = "text/html;charset=utf-8")
    public Flux<String> ask2(String topic) {
        return chatClient
                .prompt()
                .system("你是一个专业的书评助手")
                .user(u -> u.text("请给我推荐三本关于{topic}的书籍，只需要返回书名").param("topic", topic))
                .stream()
                .content();
    }

    @RequestMapping("/ask3")
    public String ask3(String question) {
        return chatClient3.prompt().user(question).call().content();
    }

    public record TopicBook(String topic, List<String> books) {

    }

    @RequestMapping(value = "/ask4")
    public String ask4(String topic) {
        TopicBook topicBook = chatClient
                .prompt()
                .system("你是一个专业的书评助手")
                .user(u -> u.text("请给我推荐三本关于{topic}的书籍，只需要返回书名").param("topic", topic))
                .call()
                .entity(TopicBook.class);
        System.out.println(topicBook);
        return "ok";
    }

    public record BookReview(
            String reviewerName, // 评书人
            int rating,// 评分
            String comment // 评论
    ) {
    }

    @RequestMapping(value = "/ask5")
    public String ask5() {
        List<BookReview> bookReviews = chatClient.prompt()
                .user(u -> u.text("请给{bookName}的书籍三条评价信息，只需要返回书名").param("bookName", "深入理解Java虚拟机"))
                .call()
                .entity(new ParameterizedTypeReference<>() {
                });
        System.out.println(bookReviews);
        return "ok";
    }

}
