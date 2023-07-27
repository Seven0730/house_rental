package com.superCode.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringBootConfiguration;

@SpringBootConfiguration
public class AutoStartProjectInDefaultBrowser implements CommandLineRunner {
    //注入项目的端口号
    @Value("${server.port}")
    private String port;

    //注入项目的名称
    @Value("${server.servlet.context-path}")
    private String context_path;

    @Override
    public void run(String... args) throws Exception {
        try {
            Runtime.getRuntime().exec("cmd /c start http://localhost:" + port + context_path + "/web/index.action");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}