package com.example.springai.controller;

import com.example.springai.util.DocumentParseUtil;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyRagController {

    @Autowired
    private VectorStore vectorStore; // 向量存储

    @Autowired
    private TokenTextSplitter tokenTextSplitter; // 文本分块器

    @Autowired
    private DocumentParseUtil documentParseUtil; // 文档解析工具

    @Autowired
    private ChatClient chatClient;

    @RequestMapping("/addDocs")
    public String addDocs(){
        String path = "/Users/fengxiao_pc/ideaProjects/aiProjects/springai/upload";
        addChunkedDocuments(path+"/Java学习.pdf", "Java学习.pdf");
        addChunkedDocuments(path+"/www.java1234.com网站简介.txt", "www.java1234.com网站简介.txt");
        addChunkedDocuments(path+"/java1234简介.docx", "java1234简介.docx");
        addChunkedDocuments(path+"/小锋老师简介.md", "小锋老师简介.md");
        return "OK";
    }

    /** 解析后按 token 切分再入库，避免单条 Document 超过嵌入模型输入上限 */
    private void addChunkedDocuments(String filePath, String label) {
        List<Document> raw = documentParseUtil.parse(filePath);
        List<Document> chunks = tokenTextSplitter.apply(raw);
        vectorStore.add(chunks);
        System.out.println(label + ",向量存储添加成功!（共 " + chunks.size() + " 块）");
    }

    @RequestMapping("/query2")
    public String query2() {
        List<Document> docs = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query("介绍下Java1234")
                        .topK(3)
                        .build());
        System.out.println(docs);
        return "OK";
    }

    @RequestMapping("/ask_rag")
    public String ask_rag(){
        // 1. 构建RAG问答Advisor（检索增强增强器）
        QuestionAnswerAdvisor qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder()
                        .topK(3) // 向量检索最多返回3条最相似文档片段
                        .similarityThreshold(0.7) // 相似度阈值0.7，低于0.7的文档直接丢弃
                        .build())
                .build();

        // 2. ChatClient调用大模型，绑定RAG检索增强器
        String result=chatClient.prompt()
                .advisors(qaAdvisor) // 挂上RAG增强逻辑
                .user("介绍下Java1234") // 用户提问
                .call()
                .content(); // 获取大模型返回的文本回答
        System.out.println("大模型响应>>>>>>>>>>>>>>>>>>>>");
        System.out.println(result);
        return "ok";
    }


}