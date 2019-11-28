package com.example.myapplication;



import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import android.util.Log;

public class emailClient
{
    private String mMailHost = "owa.magnachip.com";
    private Session mSession;

    public emailClient(String user, String pwd)
    {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", mMailHost);
        mSession = Session
                .getInstance(props, new EmailAuthenticator(user, pwd));
    } // constructor

    public void sendMail(String subject, String body, String sender,
                         String recipients)
    {
        try
        {
            Message msg = new MimeMessage(mSession);
            msg.setFrom(new InternetAddress(sender));
            msg.setSubject(subject);
            msg.setContent(body, "text/html;charset=EUC-KR");
            msg.setSentDate(new Date());
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
                    recipients));
            Transport.send(msg);
        } catch (Exception e)
        {
            Log.d("lastiverse", "Exception occured : ");
            Log.d("lastiverse", e.toString());
            Log.d("lastiverse", e.getMessage());
        } // try-catch
    } // vodi sendMail

    public void sendMailWithFile(String subject, String sender,
                                 String recipients,String body,String filePath0, String filePath1,String filePath2, String filePath3)
    {
        try
        {
            Message msg = new MimeMessage(mSession);
            msg.setFrom(new InternetAddress(sender));
            msg.setSubject(subject);
            msg.setContent(body, "text/html;charset=EUC-KR");
            msg.setSentDate(new Date());
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
                    recipients));
            MimeBodyPart attachPart = new MimeBodyPart();
            MimeBodyPart attachPart1 = new MimeBodyPart();
            MimeBodyPart attachPart2 = new MimeBodyPart();
            MimeBodyPart attachPart3 = new MimeBodyPart();
            //파일 경로를 따로 따로 지정해서 파일이름도 따로 저장해야함
            attachPart.setDataHandler(new DataHandler(new FileDataSource(
                    new File(filePath0))));

            attachPart1.setDataHandler(new DataHandler(new FileDataSource(
                    new File(filePath1))));
            attachPart2.setDataHandler(new DataHandler(new FileDataSource(
                    new File(filePath2))));
            attachPart3.setDataHandler(new DataHandler(new FileDataSource(
                    new File(filePath3))));

            Multipart multipart=new MimeMultipart();
            multipart.addBodyPart(attachPart);
            //변수 선언 주의 숫자 잘못 쓰면 뻑남 nocontent 로

            multipart.addBodyPart(attachPart1);
            attachPart1.setFileName("B.txt");
            multipart.addBodyPart(attachPart2);
            attachPart2.setFileName("G.txt");
            multipart.addBodyPart(attachPart3);
            attachPart3.setFileName("H.txt");

           msg.setContent(multipart);
            Transport.send(msg);
            Log.d("lastiverse", "sent");
        } catch (Exception e)
        {
            Log.d("lastiverse1", "Exception occured : ");
            Log.d("lastiverse2", e.toString());
            Log.d("lastiverse3", e.getMessage());
        } // try-catch
    } // void sendMailWithFile

    class EmailAuthenticator extends Authenticator
    {
        private String id;
        private String pw;

        public EmailAuthenticator(String id, String pw)
        {
            super();
            this.id = id;
            this.pw = pw;
        } // constructor

        protected PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(id, pw);
        } // PasswordAuthentication getPasswordAuthentication
    } // class EmailAuthenticator
} // class emailClient
