package com.sjw.mail;

import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * 标题：项目研发
 * <p>
 * 模块：公共部分
 * <p>
 * 公司: yyang
 * <p>
 * 作者：meteors，2009-7-3
 */
public class MailSenderInfo {

    // 发送邮件的服务器的IP和端口
    private String mailServerHost;
    private String mailServerPort = "25";
    // 邮件接收者的地址
    private String toAddress;
    // 邮件发送者的地址
    private String fromAddress;
    // 登陆邮件发送服务器的用户名和密码
    private String userName;
    private String password;
    // 是否需要身份验证
    private boolean validate = false;
    // 邮件主题
    private String subject;
    // 是否更改邮改邮件端口
    private Boolean changePort = false;
    // 邮件的文本内容
    private String content;
    // 邮件附件的文件名
    private String[] attachFileNames;

    @SuppressWarnings("unchecked")
    private List filedsList;
    @SuppressWarnings("unchecked")
    Vector file = new Vector();// 附件文件集合

    /**
     * 获得邮件会话属性
     */
    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", this.mailServerHost);
        p.put("mail.smtp.auth", validate ? "true" : "false");
        if (validate && !changePort) {
            p.put("mail.smtp.port", "994");
        }
        p.put("mail.smtp.starttls.enable", "true");
        if (validate) {
            p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            p.put("mail.smtp.socketFactory.fallback", "false");
        }
        return p;
    }

    /**
     * 方法说明：往附件组合中添加附件 输入参数： <br>
     * 返回类型：
     */
    @SuppressWarnings("unchecked")
    public void attachfile(String fname) {
        file.addElement(fname);
    }

    public String getMailServerHost() {
        return mailServerHost;
    }

    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String[] getAttachFileNames() {
        return attachFileNames;
    }

    public void setAttachFileNames(String[] fileNames) {
        this.attachFileNames = fileNames;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String textContent) {
        this.content = textContent;
    }

    @SuppressWarnings("unchecked")
    public List getFiledsList() {
        return filedsList;
    }

    @SuppressWarnings("unchecked")
    public void setFiledsList(List filedsList) {
        this.filedsList = filedsList;
    }

}
