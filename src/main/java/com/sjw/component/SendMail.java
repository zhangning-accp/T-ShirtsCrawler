package com.sjw.component;/**
 * Created by Administrator on 2018/9/17.
 */

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.sjw.mail.MailSenderInfo;
import com.sjw.tool.SimpleMailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @Author 史佳�?
 * @date 2018/9/17
 */
@Component
public class SendMail {
    @Value("${crawler.listener.email.userName}")
    private String userName;
    @Value("${crawler.listener.email.passWord}")
    private String password;
    @Value("${crawler.listener.email.serverHost}")
    private String serverHost;
    @Value("${crawler.listener.email.fromAddress}")
    private String fromAddress;


    private JSONObject send(JSONObject mail) throws Exception {
        JSONObject jsonObject = new JSONObject();
        MailSenderInfo mailSenderInfo = mail.toBean(MailSenderInfo.class);
        mailSenderInfo.setFromAddress(fromAddress);
        mailSenderInfo.setValidate(true);
        mailSenderInfo.setUserName(userName);
        mailSenderInfo.setPassword(password);
        mailSenderInfo.setMailServerHost(serverHost);
        if (mailSenderInfo.getToAddress() == null) {
            jsonObject.put("errorContent", "The toAddress is Null");
        }
        boolean sendSatus = false;
        try {
            sendSatus = SimpleMailSender.sendHtmlMail(mailSenderInfo);
            jsonObject.put("sendSatus", sendSatus);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("errorContent", e.getStackTrace());
            jsonObject.put("sendSatus", false);
        }
        jsonObject.put("requestContent", new JSONObject(mail));
        jsonObject.put("sendSatus", sendSatus);
        jsonObject.put("responseDate", DateUtil.date());
        return jsonObject;
    }

    public void SendEmail(String content, String subject, @NotNull String toAddress) {
        try {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("content", content);
            jsonObject1.put("toAddress", toAddress);
            jsonObject1.put("subject", subject);
            if(jsonObject1.getBool("sendStaus")){
                System.out.println("The email send failed");
            }
            JSONObject jsonObject = send(jsonObject1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
