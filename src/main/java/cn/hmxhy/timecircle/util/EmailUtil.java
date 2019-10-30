package cn.hmxhy.timecircle.util;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class EmailUtil {
	@Data
	private static
	class EmailInfo {
		private String hostName = "smtp.qq.com";
		private String protocol = "smtp";
		private String auth = "true";
		private String port = "465";
		private String debug = "true";
		private String ssl = "true";
		private String emailName = "****";
		private String name = "TimeCircle";
		private String userName = "****";
		private String password = "****";
	}

	private static EmailInfo emailInfo = new EmailInfo();

	private static Map<String, Object> sendEmailBefor() {
		Properties prop = new Properties();
		prop.setProperty("mail.host", emailInfo.getHostName());
		prop.setProperty("mail.transport.protocol", emailInfo.getProtocol());
		prop.setProperty("mail.smtp.auth", emailInfo.getAuth());
		prop.setProperty("mail.smtp.port", emailInfo.getPort());//发送端口
		prop.setProperty("mail.debug", emailInfo.getDebug());//true 打印信息到控制台
		prop.setProperty("mail.smtp.ssl.enable", emailInfo.getSsl());
		//使用JavaMail发送邮件的5个步骤
		//jquery、创建session
		Session session = Session.getInstance(prop);
		//开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		session.setDebug(true);
		//2、通过session得到transport对象
		Transport ts = null;
		MimeMessage message = null;
		try {
			ts = session.getTransport();
			//3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
			ts.connect(emailInfo.getHostName(), emailInfo.getUserName(), emailInfo.getPassword());
			//4、创建邮件
			message = new MimeMessage(session);
			//指明邮件的发件人
			message.setFrom(new InternetAddress(emailInfo.getUserName(), emailInfo.getName()));
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}


		Map<String, Object> mailInfo = new HashMap<String, Object>();
		mailInfo.put("session", session);
		mailInfo.put("message", message);
		mailInfo.put("transport", ts);
		return mailInfo;
	}

	private static void sendEmailAfter(Transport ts, Message message) {
		//5、发送邮件
		try {
			ts.sendMessage(message, message.getAllRecipients());
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			try {
				ts.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 发送只有文本内容的邮件
	 *
	 * @param recipient 收件人
	 * @param title     标题
	 * @param content   内容
	 * @return
	 */
	public static JSONObject sendTextEmail(String recipient, String title, String content) {
		sendTextAndImageAndMixedEmail(recipient, title, content, null, null);
		return null;
	}

	/**
	 * 发送只有图片内容的邮件
	 *
	 * @param recipient     收件人
	 * @param title         标题
	 * @param imageAddresss 图片
	 * @return
	 */
	public static JSONObject sendImageEmail(String recipient, String title, List<String> imageAddresss) {
		sendTextAndImageEmail(recipient, title, "", imageAddresss);
		return null;
	}

	/**
	 * 发送只有附件的邮件
	 *
	 * @param recipient     收件人
	 * @param title         标题
	 * @param mixedAddresss 附件
	 * @return
	 */
	public static JSONObject sendMixedEmail(String recipient, String title, List<String> mixedAddresss) {
		sendTextAndMixedEmail(recipient, title, "", mixedAddresss);
		return null;
	}

	/**
	 * 发送包含文本和图片的邮件
	 *
	 * @param recipient     收件人
	 * @param title         标题
	 * @param content       内容
	 * @param imageAddresss 图片
	 * @return
	 */
	public static JSONObject sendTextAndImageEmail(String recipient, String title, String content, List<String> imageAddresss) {
		sendTextAndImageAndMixedEmail(recipient, title, content, imageAddresss, null);
		return null;
	}

	/**
	 * 发送文本加附件
	 *
	 * @param recipient     收件人
	 * @param title         标题
	 * @param content       内容
	 * @param mixedAddresss 附件
	 * @return
	 */
	public static JSONObject sendTextAndMixedEmail(String recipient, String title, String content, List<String> mixedAddresss) {
		sendTextAndImageAndMixedEmail(recipient, title, content, null, mixedAddresss);
		return null;
	}

	/**
	 * 发送图片和附件邮件
	 *
	 * @param recipient     收件人
	 * @param title         标题
	 * @param imageAddresss 图片地址
	 * @param mixedAddresss 附件地址
	 * @return
	 */
	public static JSONObject sendImageAndMixedEmail(String recipient, String title, List<String> imageAddresss, List<String> mixedAddresss) {
		sendTextAndImageAndMixedEmail(recipient, title, "", imageAddresss, mixedAddresss);
		return null;
	}

	/**
	 * 发送文本内容、图片和附件邮件
	 *
	 * @param recipient     收件人
	 * @param title         标题
	 * @param content       内容
	 * @param imageAddresss 图片地址
	 * @param mixedAddresss 附件地址
	 * @return
	 */
	public static JSONObject sendTextAndImageAndMixedEmail(String recipient, String title, String content, List<String> imageAddresss, List<String> mixedAddresss) {
		Map<String, Object> mailInfo = sendEmailBefor();
		Transport transport = (Transport) mailInfo.get("transport");
		MimeMessage message = (MimeMessage) mailInfo.get("message");
		//4、创建邮件
		//收件人
		try {
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			//邮件标题
			message.setSubject(title);

			MimeMultipart mimeMultipart = new MimeMultipart();
			MimeBodyPart text = new MimeBodyPart();

			if (imageAddresss != null) {
				MimeMultipart mm = new MimeMultipart();
				for (int i = 0; i < imageAddresss.size(); i++) {
					content += "<img src=\'cid:" + i + "'></img></br>";
				}
				text.setContent(content, "text/html;charset=UTF-8");
				int i = 0;
				for (String imgFile : imageAddresss) {
					// 准备图片数据
					MimeBodyPart image = new MimeBodyPart();
					DataHandler dh = new DataHandler(new FileDataSource(imgFile));
					image.setDataHandler(dh);
					image.setContentID(i + "");
					image.setFileName(dh.getName());
					mm.addBodyPart(image);
					i++;
				}
				mm.addBodyPart(text);
				mm.setSubType("related");
				MimeBodyPart c = new MimeBodyPart();
				c.setContent(mm);
				mimeMultipart.addBodyPart(c);
			} else {
				text.setContent(content, "text/html;charset=UTF-8");
				mimeMultipart.addBodyPart(text);
			}

			if (mixedAddresss != null) {
				for (String fileAddress : mixedAddresss) {
					MimeBodyPart attach = new MimeBodyPart();
					DataHandler dh = new DataHandler(new FileDataSource(fileAddress));
					attach.setDataHandler(dh);
					attach.setFileName(dh.getName());
					mimeMultipart.addBodyPart(attach);
				}
				mimeMultipart.setSubType("mixed");
			}
			message.setContent(mimeMultipart);
			message.saveChanges();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		sendEmailAfter(transport, message);
		return null;
	}

	private static EmailInfo updateEmailInfo(EmailInfo emailInfo) {
		return emailInfo;
	}
}
