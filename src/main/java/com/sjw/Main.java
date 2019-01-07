package com.sjw;

import com.sjw.crawler.lancher.PLancher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Administrator on 2018/12/21.
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class Main {
    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);
//            ImgLancher bean = run.getBean(ImgLancher.class);
//            bean.startDownload();
            PLancher bean = run.getBean(PLancher.class);
            bean.stratCrawler();
            //邮件发送测试
//            SendMail bean = run.getBean(SendMail.class);
//            JSONObject jsonObject1 = new JSONObject();
//            jsonObject1.put("content","success");
//            jsonObject1.put("toAddress","shijiawei@yintatech.com");
//            jsonObject1.put("subject","success");
//            JSONObject jsonObject = bean.sendMail(jsonObject1);
//            Boolean sendSatus = jsonObject.getBool("sendSatus");
//            System.out.println(jsonObject.get("errorContent"));
//            System.out.println(sendSatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ;
}
