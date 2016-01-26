package organizer.logic.impl.security;

import org.springframework.stereotype.Service;
import organizer.models.User;

import java.util.HashMap;
import java.util.Map;


public class RestorePasswordMap {
	private Map<String,User> restorPasswordMap = new HashMap<String, User>();

	public void addRestore(String verify, User user){
		restorPasswordMap.put(verify,user);
	}

	private void dropRestore(String verify){
		restorPasswordMap.remove(verify);
	}

	public User verify(String verify){
		if(restorPasswordMap.containsKey(verify)) {
			User user = restorPasswordMap.get(verify);
			dropRestore(verify);
			return user;
		}
		return null;
	}
}
