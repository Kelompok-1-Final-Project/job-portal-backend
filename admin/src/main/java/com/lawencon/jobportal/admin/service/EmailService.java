package com.lawencon.jobportal.admin.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(String to, String subject, String body){
		
		Thread thread = new Thread(){
            public void run(){
                SimpleMailMessage msg = new SimpleMailMessage();
				msg.setTo(to);
				msg.setSubject(subject);
				msg.setText(body);
				javaMailSender.send(msg);
            }
        };
        
        thread.start();
	}
	
	public void sendMailWithAttachment(String to, String subject, String body, byte[] fileToAttach, String fileName) {
		try {
			final MimeMessage message = javaMailSender.createMimeMessage();
			final MimeMessageHelper mime = new MimeMessageHelper(message, true);
			mime.setTo(to);
			mime.setSubject(subject);
			mime.setText(body);
			mime.addAttachment(fileName + ".pdf", new ByteArrayResource(fileToAttach));

			javaMailSender.send(message);
//	    	javaMailSender.send(preparator);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(ex.getMessage());
		}
	}
}
