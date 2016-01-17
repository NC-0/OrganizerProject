package organizer.logic.impl.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import organizer.dao.api.UserDao;
import organizer.logic.impl.MessageContent;
import organizer.models.Task;

import javax.mail.MessagingException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class SheduledSender {
	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDaoImpl;

	@Autowired
	@Qualifier("mailSender")
	private MailSender mailSender;

	@Scheduled(cron = "01 26 20 * * *")
	public void reminder() throws MessagingException {
		MailTasks mailTasks = userDaoImpl.getTommorowTasks();
		Set<String> mails = mailTasks.getTaskMultimap().keySet();
		Iterator<String> iterator = mails.iterator();
		while(iterator.hasNext()){
			String mail = iterator.next();
			List<Task> taskList = (List<Task>) mailTasks.getTaskMultimap().get(mail);
			String text="";
			for(int j=0;j<taskList.size();j++)
				text=text+String.format(MessageContent.EMAIL_TASK,taskList.get(j).getName());
			mailSender.sendMail(mail,text,MessageContent.MAIL_SUBJECT_NOTIFY,MessageContent.EMAIL_TASK_NOTIFY);
		}
	}
}
