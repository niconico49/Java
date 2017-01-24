/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package easy.api.service;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author development
 */
public class ComponentMail {

    private Session mailSession;
    private Message message;
    private Multipart multipart;
    
    private String host;
    private String port;
    private String login;
    private String password;

    private String mailFrom;
    private String mailTo;
    private String mailCc;
    private String mailBcc;

    private String subject;
    private String body;

    public ComponentMail getInstance() {
        return new ComponentMail();
    }
    
    public void setHost(String value) {
        this.host = value;
    }
    
    public void setPort(String value) {
        this.port = value;
    }
    
    public void setLogin(String value) {
        this.login = value;
    }
    
    public void setPassword(String value) {
        this.password = value;
    }
    
    public void setMailFrom(String value) {
        this.mailFrom = value;
    }
    
    public void setMailTo(String value) {
        this.mailTo = value;
    }
    
    public void setMailCc(String value) {
        this.mailCc = value;
    }

    public void setMailBcc(String value) {
        this.mailBcc = value;
    }

    public void setSubject(String value) {
        this.subject = value;
    }

    public void setBody(String value) {
        this.body = value;
    }

    private void setRecipients(String mail, Message message, Message.RecipientType recipientType) 
        throws MessagingException {
        if (mail != null && mail.length() > 0) {
            message.setRecipients(recipientType, InternetAddress.parse(mail.replace(';', ',')));
        }
    }

    public void prepare() {
		Properties props = new Properties();
		//props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", this.host);
		props.put("mail.smtp.port", this.port);
 
		this.mailSession = Session.getInstance(props);
/*		
		final String loginFinal = login;
		final String passwordFinal = password;

		Session mailSession = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(loginFinal, passwordFinal);
			}
		  });
 */
        try {
			this.message = new MimeMessage(this.mailSession);
			
			this.message.setSubject(this.subject);

			this.message.setFrom(new InternetAddress(this.mailFrom));
            
            setRecipients(this.mailTo, this.message, Message.RecipientType.TO);
            setRecipients(this.mailCc, this.message, Message.RecipientType.CC);
            setRecipients(this.mailBcc, this.message, Message.RecipientType.BCC);
            
            this.multipart = new MimeMultipart("related");
			
			BodyPart htmlPart  = new MimeBodyPart();
			htmlPart.setContent(this.body, "text/html");

			this.multipart.addBodyPart(htmlPart); 
		}
		catch (MessagingException e) {
			String result = e.getMessage();
		}
    }

    public void addContentId(String key, String value) {
        String contentId = key;
        String path = value;

        //DataSource getURLResource(java.net.URL url, java.lang.String name)
        DataSource dataSource = new FileDataSource(path);
        //URLDataSource urlDataSource = new URLDataSource(new URL(path));

        BodyPart imgPart = new MimeBodyPart();

        try {
            // Loading the image
            //imgPart.setDataHandler(new DataHandler(urlDataSource));
            imgPart.setDataHandler(new DataHandler(dataSource));

            //Setting the header
            imgPart.setHeader("Content-ID", contentId);

            this.multipart.addBodyPart(imgPart);
		}
		catch (MessagingException e) {
			String result = e.getMessage();
		}
    }

    public String send() {
        String result = "";

        try {
            // attaching the multi-part to the message
			this.message.setContent(this.multipart);

            Transport transport = this.mailSession.getTransport("smtp");
			transport.connect(this.login, this.password);
			transport.sendMessage(this.message, this.message.getAllRecipients());
			transport.close();
            
		}
		catch (MessagingException e) {
			result = e.getMessage();
		}
		
		return result;
    }
}
