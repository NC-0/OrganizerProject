package organizer.logic.impl.customvalidators;

import organizer.logic.api.ValidString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class StringValidator implements ConstraintValidator<ValidString,String> {
	public void initialize(ValidString validString) {

	}

	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		String valid = s.trim();
		if(valid.length()==0)
			return false;
		if(Pattern.compile(">|<|/|'").matcher(valid).find()){
			return false;
		}
		return true;
	}
}
