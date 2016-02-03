package organizer.logic.impl;


public class MessageContent {
	
	public final static String USER_CREATED = "New user %s created";
	
	public final static String USER_ALREADY_EXIST = "User with '%s' email already exist";

	public final static String USER_NOT_EXIST = "User with '%s' email not exist";
	
	public final static String ERROR = "Something bad happens, sorry";

	public final static String USER_INVALID_LOGIN = "Invalid username and password!";

	public final static String USER_UPDATED = "You profile has been updated!";

	public final static String PASSWORD_ERROR = "Passwords doesn't match";

	public final static String EMAIL_ERROR = "Can't send mail";

	public final static String EMAIL_SENDED = "Email successfully sended";

	public final static String MAIL = "organizer365@outlook.com";

	public final static String MAIL_SUBJECT_VERIFICATION = "SuperOrganizer.com email verification";

	public final static String MAIL_SUBJECT_FORGET_PASS = "SuperOrganizer.com restore password";

	public final static String MAIL_SUBJECT_NOTIFY = "SuperOrganizer.com task notification";

	public final static String MAIL_TEXT_VERIFICATION = "<html><body><b>Hello, %s! You succesfully registered on SuperOrganizer.com. Please go to this link <a href='http://localhost:8081/verification?verific=%s'>http://localhost:8081/verification?verific=%s</a></body></html>";

	public final static String MAIL_TEXT_FORGET = "<html><body><b>Hello %s! You forgot password on SuperOrganizer.com.<br> You new password: %s<br> ";

	public final static String MAIL_PASS_CONFIRMATION = "If it was you, then confirm changes by this link <a href='http://localhost:8081/restoreverify?restore=%s'>http://localhost:8081/restoreverify?restore=%s</a>.</body></html>";

	public final static String VERIFY_ERROR = "Unrecognize verification id";

	public final static String VERIFY_SUCCESSFULL = "Verification successful";

	public final static String EMAIL_TASK_NOTIFY = "<html><body><b>Hello, %s!<br> You tasks on tomorrow:<br><table>%s</table></body></html>";

	public final static String EMAIL_TASK = "<tr><td bgcolor='%s'>%s</td></tr>";

	public final static String EMAIL_PASS = "New password send to your email";
}
