package organizer.logic.api;

import organizer.logic.impl.customvalidators.StringValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = StringValidator.class)
@Documented
public @interface ValidString {
	String message() default "String is not valid";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
