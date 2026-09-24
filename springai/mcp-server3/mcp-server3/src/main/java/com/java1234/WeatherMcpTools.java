package com.java1234;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

@Component
public class WeatherMcpTools {

    @McpTool(
            name = "getWeatherForecast",
            description = "根据城市名称查询模拟天气预报（演示数据，非真实气象来源）",
            generateOutputSchema = true
    )
    public String getWeatherForecast(
            @McpToolParam(description = "城市名称，例如：北京、上海", required = true) String city) {
        System.out.println("执行到天气预报MCP-SERVER");
        return city + " 天气晴天 空气质量优。";
    }
}
