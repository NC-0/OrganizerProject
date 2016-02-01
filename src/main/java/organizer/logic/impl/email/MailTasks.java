package organizer.logic.impl.email;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import organizer.models.Task;

import java.util.ArrayList;

public final class MailTasks{
	private Multimap<String,Task> taskMultimap;

	public MailTasks() {
		this.taskMultimap = ArrayListMultimap.create();
	}

	public Multimap<String, Task> getTaskMultimap() {
		return taskMultimap;
	}

}