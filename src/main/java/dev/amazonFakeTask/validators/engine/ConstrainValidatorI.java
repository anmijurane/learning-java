package dev.amazonFakeTask.validators.engine;

import java.lang.annotation.Annotation;

public interface ConstrainValidatorI<A extends Annotation> {
  boolean isValid(A annotation, Object value);

  String getMessage(A annotation);
}
