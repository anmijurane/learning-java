package dev.amazonFakeTask.validators.handlers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.amazonFakeTask.validators.engine.ConstrainValidatorI;
import dev.amazonFakeTask.validators.engine.Constraint;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = Min.Validator.class)
public @interface Min {
  double value();

  String message() default "The field must be greater than %s";

  class Validator implements ConstrainValidatorI<Min> {
    @Override
    public boolean isValid(Min annotation, Object value) {
      if (value == null)
        return false;

      if (value instanceof Number number) {
        return number.longValue() >= annotation.value();
      }
      return false;
    }

    @Override
    public String getMessage(Min annotation) {
      return String.format(annotation.message(), annotation.value());
    }

  }

}
