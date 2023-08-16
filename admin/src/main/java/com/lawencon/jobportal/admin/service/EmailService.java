package com.lawencon.jobportal.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dto.InsertResDto;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public InsertResDto sendEmail(String to, String subject, String body){
		System.out.println("Sending Email...");
		
		final SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);

		msg.setSubject(subject);
		msg.setText(body);

		javaMailSender.send(msg);

		System.out.println("Done");

		final InsertResDto insertResDto = new InsertResDto();

		insertResDto.setMessage("Kirim Email Berhasil");

		return insertResDto;
	}
}
