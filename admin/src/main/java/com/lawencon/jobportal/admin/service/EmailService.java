package com.lawencon.jobportal.admin.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.lawencon.jobportal.admin.model.Assessment;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.Interview;
import com.lawencon.jobportal.admin.model.MedicalCheckup;
import com.lawencon.jobportal.admin.model.Offering;
import com.lawencon.jobportal.admin.model.User;

@Service
public class EmailService {
	private static final String INLOOK_LOGO_IMAGE = "templates/images/logo.png";
	private static final String PNG_MIME = "images/png";
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private TemplateEngine htmlTemplateEngine;

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
	
	public void sendEmailNewUser(String subject, User user, String password) {

		Thread thread = new Thread() {
			public void run() {

				try {
					String loginUrl = "http://localhost:4200/login";

					final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
					final MimeMessageHelper email;
					email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

					email.setTo(user.getEmail());
					email.setSubject(subject);

					final Context ctx = new Context(LocaleContextHolder.getLocale());
					ctx.setVariable("email", user.getEmail());
					ctx.setVariable("password", password);
					ctx.setVariable("inLookLogo", INLOOK_LOGO_IMAGE);
					ctx.setVariable("url", loginUrl);

					final String htmlContent = htmlTemplateEngine.process("RegisterUser", ctx);
					email.setText(htmlContent, true);

					ClassPathResource clr = new ClassPathResource(INLOOK_LOGO_IMAGE);
					email.addInline("inLookLogo", clr, PNG_MIME);

					javaMailSender.send(mimeMessage);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

			}
		};

		thread.start();
	}
	
	public void sendEmailNewCandidate(String subject, Candidate candidate, String password) {

		Thread thread = new Thread() {
			public void run() {

				try {
					String loginUrl = "http://localhost:4200/login";

					final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
					final MimeMessageHelper email;
					email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

					email.setTo(candidate.getEmail());
					email.setSubject(subject);

					final Context ctx = new Context(LocaleContextHolder.getLocale());
					ctx.setVariable("email", candidate.getEmail());
					ctx.setVariable("password", password);
					ctx.setVariable("inLookLogo", INLOOK_LOGO_IMAGE);
					ctx.setVariable("url", loginUrl);

					final String htmlContent = htmlTemplateEngine.process("RegisterUser", ctx);
					email.setText(htmlContent, true);

					ClassPathResource clr = new ClassPathResource(INLOOK_LOGO_IMAGE);
					email.addInline("inLookLogo", clr, PNG_MIME);

					javaMailSender.send(mimeMessage);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

			}
		};

		thread.start();
	}
	
	public void sendEmailAssessment(String subject, Candidate candidate, Assessment assessment) {

		Thread thread = new Thread() {
			public void run() {

				try {
					String loginUrl = "http://localhost:4200/login";

					final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
					final MimeMessageHelper email;
					email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

					email.setTo(candidate.getEmail());
					email.setSubject(subject);

					final Context ctx = new Context(LocaleContextHolder.getLocale());
					ctx.setVariable("name", candidate.getCandidateProfile().getFullName());
					ctx.setVariable("position", assessment.getJob().getJobPosition().getPositionName());
					ctx.setVariable("company", assessment.getJob().getCompany().getCompanyName());
					ctx.setVariable("address", assessment.getJob().getCompany().getAddress());
					ctx.setVariable("city", assessment.getJob().getCompany().getCity().getCityName());
					ctx.setVariable("date", assessment.getSchedule().toString());
					ctx.setVariable("inLookLogo", INLOOK_LOGO_IMAGE);
					ctx.setVariable("url", loginUrl);

					final String htmlContent = htmlTemplateEngine.process("Assessment", ctx);
					email.setText(htmlContent, true);

					ClassPathResource clr = new ClassPathResource(INLOOK_LOGO_IMAGE);
					email.addInline("inLookLogo", clr, PNG_MIME);

					javaMailSender.send(mimeMessage);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

			}
		};

		thread.start();
	}
	
	public void sendEmailInterview(String subject, Candidate candidate, Interview interview) {

		Thread thread = new Thread() {
			public void run() {

				try {
					String loginUrl = "http://localhost:4200/login";

					final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
					final MimeMessageHelper email;
					email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

					email.setTo(candidate.getEmail());
					email.setSubject(subject);

					final Context ctx = new Context(LocaleContextHolder.getLocale());
					ctx.setVariable("name", candidate.getCandidateProfile().getFullName());
					ctx.setVariable("position", interview.getJob().getJobPosition().getPositionName());
					ctx.setVariable("company", interview.getJob().getCompany().getCompanyName());
					ctx.setVariable("address", interview.getJob().getCompany().getAddress());
					ctx.setVariable("city", interview.getJob().getCompany().getCity().getCityName());
					ctx.setVariable("date", interview.getSchedule().toString());
					ctx.setVariable("inLookLogo", INLOOK_LOGO_IMAGE);
					ctx.setVariable("url", loginUrl);

					final String htmlContent = htmlTemplateEngine.process("Interview", ctx);
					email.setText(htmlContent, true);

					ClassPathResource clr = new ClassPathResource(INLOOK_LOGO_IMAGE);
					email.addInline("inLookLogo", clr, PNG_MIME);

					javaMailSender.send(mimeMessage);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

			}
		};

		thread.start();
	}
	
	public void sendEmailMedicalCheckup(String subject, Candidate candidate, MedicalCheckup medicalCheckup) {

		Thread thread = new Thread() {
			public void run() {

				try {
					String loginUrl = "http://localhost:4200/login";

					final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
					final MimeMessageHelper email;
					email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

					email.setTo(candidate.getEmail());
					email.setSubject(subject);

					final Context ctx = new Context(LocaleContextHolder.getLocale());
					ctx.setVariable("name", candidate.getCandidateProfile().getFullName());
					ctx.setVariable("position", medicalCheckup.getJob().getJobPosition().getPositionName());
					ctx.setVariable("company", medicalCheckup.getJob().getCompany().getCompanyName());
					ctx.setVariable("inLookLogo", INLOOK_LOGO_IMAGE);
					ctx.setVariable("url", loginUrl);

					final String htmlContent = htmlTemplateEngine.process("MCU", ctx);
					email.setText(htmlContent, true);

					ClassPathResource clr = new ClassPathResource(INLOOK_LOGO_IMAGE);
					email.addInline("inLookLogo", clr, PNG_MIME);

					javaMailSender.send(mimeMessage);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

			}
		};

		thread.start();
	}
	
	public void sendEmailOffering(String subject, Candidate candidate, Offering offering, byte[] attachment) {

		Thread thread = new Thread() {
			public void run() {

				try {
					String loginUrl = "http://localhost:4200/login";

					final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
					final MimeMessageHelper email;
					email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

					email.setTo(candidate.getEmail());
					email.setSubject(subject);
					email.addAttachment("Offering Letter", new ByteArrayResource(attachment));

					final Context ctx = new Context(LocaleContextHolder.getLocale());
					ctx.setVariable("name", candidate.getCandidateProfile().getFullName());
					ctx.setVariable("position", offering.getJob().getJobPosition().getPositionName());
					ctx.setVariable("company", offering.getJob().getCompany().getCompanyName());
					ctx.setVariable("inLookLogo", INLOOK_LOGO_IMAGE);
					ctx.setVariable("url", loginUrl);

					final String htmlContent = htmlTemplateEngine.process("MCU", ctx);
					email.setText(htmlContent, true);

					ClassPathResource clr = new ClassPathResource(INLOOK_LOGO_IMAGE);
					email.addInline("inLookLogo", clr, PNG_MIME);

					javaMailSender.send(mimeMessage);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

			}
		};

		thread.start();
	}
}
