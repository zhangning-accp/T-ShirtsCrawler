package com.sjw.tool;

import com.sjw.component.MyAuthenticator;
import com.sjw.mail.EnergyConstants;
import com.sjw.mail.MailSenderFilesInfo;
import com.sjw.mail.MailSenderInfo;

import javax.activation.DataHandler;
import javax.activation.FileTypeMap;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 *
 * 标题：项目研发
 *
 * 模块：公共部分
 *
 * 公司: yyang
 *
 * 作者：meteors，2009-7-3
 *
 * 描述：简单邮件（不带附件的邮件）发送器
 *
 * 说明:
 *
 */
public class SimpleMailSender {
    /**
     * 以文本格式发送邮件
     *
     * @param mailInfo
     *            待发送的邮件的信息
     */
    public boolean sendTextMail(MailSenderInfo mailInfo) throws Exception {
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            pro.put("mail.smtp.auth", "true");
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容
            String mailContent = mailInfo.getContent();

            mailMessage.setText(mailContent);
            // 发送邮件
            Transport.send(mailMessage);

            mailMessage.saveChanges(); // implicit with send()
            Transport transport = sendMailSession.getTransport("smtp");
            transport.connect("mail.htf.com.cn", mailInfo.getUserName(), mailInfo.getPassword());
            transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
            transport.close();

            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * 以HTML格式发送邮件
     *
     * @param mailInfo
     *            待发送的邮件信息
     */
    public static boolean sendHtmlMail(MailSenderInfo mailInfo) throws Exception {
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        // 如果需要身份认证，则创建一个密码验证器
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(mailInfo.getToAddress());
            // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题
            mailMessage.setSubject(new String(mailInfo.getSubject()!=null?mailInfo.getSubject().getBytes():"".getBytes(),"GBK"));
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(new String(mailInfo.getContent()!=null?mailInfo.getContent().getBytes():"".getBytes(),"GBK"), "text/html; charset=GBK");
            mainPart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * 发送含有附件的邮件
     *
     * @param mailInfo
     *            信息列表
     * @param flag
     *            标识字段，null，0为流读取，1为blob读取
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean sendFilesMail(MailSenderInfo mailInfo, String flag) throws Exception {
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        // 如果需要身份认证，则创建一个密码验证器
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(mailInfo.getToAddress());
            // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
            List fileslist = mailInfo.getFiledsList();
            // 附件
            if (fileslist != null) {
                int filsesize = fileslist.size();
                for (int i = 0; i < filsesize; i++) {
                    InputStream is = null;
                    BodyPart attachmentPart = new MimeBodyPart(); // 封装附件
                    MailSenderFilesInfo files = (MailSenderFilesInfo) fileslist.get(i);
                    String filesname = files.getName();
                    String contentType = FileTypeMap.getDefaultFileTypeMap().getContentType(filesname);
                    String filename = "";
                    if (!contentType.equals("")) {
                        try {
                            filename = MimeUtility.encodeText(filesname);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        attachmentPart.setFileName(filename);
                        if (StrUtil.isEmptyStr(flag) || EnergyConstants.SYSTEM_ZT_00.equals(flag)) {
                            is = files.getFileInputStream();
                        } else {
                            is = files.getFileobject().getBinaryStream();
                        }
                        //
                        ByteArrayDataSource bads = null;
                        try {
                            bads = new ByteArrayDataSource(is, contentType);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        DataHandler dataHandler = new DataHandler(bads);
                        attachmentPart.setDataHandler(dataHandler);

                        mainPart.addBodyPart(attachmentPart);
                    } else {
                        continue;
                    }

                }
            }
            mailMessage.setContent(mainPart);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;

        }
    }
}
