package dev.amazonFakeTask.validators.handlers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.amazonFakeTask.validators.engine.ConstrainValidatorI;
import dev.amazonFakeTask.validators.engine.Constraint;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotNull.Validator.class)
public @interface NotNull {
  String message() default "field cannot be null";

  class Validator implements ConstrainValidatorI<NotNull> {
    @Override
    public boolean isValid(NotNull annotation, Object value) {
      return value != null;
    }

    @Override
    public String getMessage(NotNull annotation) {
      return annotation.message();
    }
  }

}
