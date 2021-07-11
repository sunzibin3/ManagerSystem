package com.sun.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
public class MailTestController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     *  发送普通邮件
     * @param to 邮件接收者
     * @param subject 邮件主题
     * @param msg 邮件内容
     * @return
     */
    @RequestMapping("/sendMail/{to}/{subject}/{msg}")
    public Boolean sendMail(@PathVariable(value ="to" ) String to,
                            @PathVariable(value ="subject" ) String subject,
                            @PathVariable(value = "msg") String msg){
        System.out.println(to+"#########"+subject+"############"+msg);
        SimpleMailMessage message =new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        message.setFrom(from);

        javaMailSender.send(message);
        return Boolean.TRUE;
    }

    /**
     *  发送带有附件的邮件
     * @param to 邮件接收者
     * @param subject 邮件主题
     * @param msg 邮件内容
     * @param file 附件文件
     * @return
     */
    @RequestMapping("/sendMail2/{to}/{subject}/{msg}")
    public Boolean sendMail2(@PathVariable(value ="to" ) String to,
                             @PathVariable(value ="subject" ) String subject,
                             @PathVariable(value = "msg") String msg,
                             @RequestParam("file")MultipartFile file) throws MessagingException {
        System.out.println(to+"#########"+subject+"############"+msg);
        MimeMessage message = javaMailSender.createMimeMessage();
//        SimpleMailMessage message =new SimpleMailMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(msg);
        messageHelper.setFrom(from);

        messageHelper.addAttachment("test.txt", file);
        javaMailSender.send(message);
        return Boolean.TRUE;
    }

    /**
     * HTML 文本邮件
     * @param to 接收者邮件
     * @param subject 邮件主题
     * @param msg HTML内容
     * @throws MessagingException
     */
    @RequestMapping("/sendMail3/{to}/{subject}")
    public Boolean sendHtmlMail(@PathVariable(value ="to" ) String to,
                             @PathVariable(value ="subject" ) String subject,
                             @RequestParam(value = "msg") String msg) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(msg, true);//第二个参数为True,开启Html邮件
        helper.setFrom(from);

        javaMailSender.send(message);
        return Boolean.TRUE;
    }
}
