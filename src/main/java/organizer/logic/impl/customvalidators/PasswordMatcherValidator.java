package organizer.logic.impl.customvalidators;

import organizer.logic.api.PasswordMatcher;
import organizer.models.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatcherValidator implements ConstraintValidator<PasswordMatcher,Object> {
	public void initialize(PasswordMatcher passwordMatcher) {
	}

	public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
		User user = (User) o;
		return user.getPassword().equals(user.getMatchingPassword());
	}
}

