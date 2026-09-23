package com.example.springai.controller;

import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class MyEmController {

    @Autowired
    private OpenAiEmbeddingModel embeddingModel;

    @RequestMapping("/em")
    public String em(){
        float[] textVector1 = embeddingModel.embed("学Java上java1234.com");
        float[] textVector2 = embeddingModel.embed("java1234.com网站教学Java质量真不错");
        float[] textVector3 = embeddingModel.embed("我喜欢吃苹果");
        System.out.println(textVector1.length+" "+Arrays.toString(textVector1));
        System.out.println(textVector2.length+" "+Arrays.toString(textVector2));
        System.out.println(textVector3.length+" "+Arrays.toString(textVector3));
        double dist12 =euclideanDistance(textVector1, textVector2);
        double dist13 =euclideanDistance(textVector1, textVector3);
        System.out.println("1到2的欧氏距离:"+dist12);
        System.out.println("1到3的欧氏距离:"+dist13);
        return "ok";
    }

    /**
     * 计算两个向量的欧式距离
     * @param vec1 向量1
     * @param vec2 向量2
     * @return 欧式距离
     */
    public static double euclideanDistance(float[] vec1, float[] vec2) {
        if (vec1.length != vec2.length) {
            throw new IllegalArgumentException("向量维度必须相同");
        }
        double sum = 0.0;
        for (int i = 0; i < vec1.length; i++) {
            double diff = vec1[i] - vec2[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}
