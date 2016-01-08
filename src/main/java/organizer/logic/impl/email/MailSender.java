package organizer.logic.impl.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import organizer.logic.impl.MessageContent;
import organizer.models.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSender {

	@Autowired
	@Qualifier("simpleMailSender")
	private JavaMailSender simpleMailSender;

	@Async
	public void sendMail(User user,String verificationId) throws MessagingException{
		simpleMailSender.send(createCustomMessage(user,verificationId));
	}

	public MimeMessage createCustomMessage(User user,String verificationId) throws  MessagingException{
		MimeMessage message = simpleMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
		messageHelper.setFrom(MessageContent.MAIL);
		messageHelper.setTo(user.getEmail());
		messageHelper.setSubject(MessageContent.MAIL_SUBJECT_VERIFICATION);
		messageHelper.setText(String.format(MessageContent.MAIL_TEXT_VERIFICATION, user.getEmail(),verificationId,verificationId),true);
		return message;
	}
}