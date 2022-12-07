package com.brs.service;

import com.brs.entities.EmailDetails;
import com.brs.util.BatchStatusInterf;

public interface EmailService {

	
	String sendSimpleMail(EmailDetails details);
	
	 String sendMailWithAttachment(EmailDetails details);
}
