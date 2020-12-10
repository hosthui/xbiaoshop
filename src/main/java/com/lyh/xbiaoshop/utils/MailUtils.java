package com.lyh.xbiaoshop.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.Random;

/**
 * @author:WZJun
 * @date: 2016/2/15
 */
public class MailUtils {

	public static void main(String[] args) throws Exception {


		sendMail("yun89688@gmail.com",verifyCode());

	}

	public static String verifyCode(){
		Random random = new Random();
		char[] set = {91,92,93,94,95,96,58,59,60,61,62,63,64};
		String str = "";
		while (str.length() != 6){
			boolean flag = true;
			int a = (random.nextInt(75) + 48);
			for (int j = 0; j < set.length; j++){
				if (a == set[j]){
					flag = false;
				}
			}
			if (flag){
				char ch = (char) a;
				//System.out.println(a);
				str += ch;
			}
		}
		return str;
	}
	public static void sendMail(String emailName, String code) {

		//第一步：设置发件人邮箱地址，第二步：开启smtp服务
		try {
			//设置发件人(服务器)
			String from = "252770908@qq.com";
			//设置收件人
			String to = emailName;
			//设置邮件发送的服务器，这里为QQ邮件服务器
			String host = "smtp.qq.com";
			//获取系统属性
			Properties properties = System.getProperties();
			//SSL加密
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.ssl.socketFactory", sf);

			//设置系统属性
			properties.setProperty("mail.smtp.host", host);
			properties.put("mail.smtp.auth", "true");

			//获取发送邮件会话、获取第三方登录授权码
			Session session = Session.getDefaultInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					//第三方登录授权码
					return new PasswordAuthentication(from, "qgwrcorbuerccaih");
				}
			});
			Message message = new MimeMessage(session);
			//防止邮件被当然垃圾邮件处理，披上Outlook的马甲
			message.addHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.2869");
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			//邮件标题
			message.setSubject("小标会议忘记密码验证码");
			BodyPart bodyPart = new MimeBodyPart();
			//消息体
			bodyPart.setText("修改密码验证码：" + String.valueOf(code));
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);
			message.setContent(multipart);
			Transport.send(message);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
