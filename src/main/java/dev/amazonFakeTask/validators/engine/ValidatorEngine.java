package dev.amazonFakeTask.validators.engine;

import java.lang.reflect.Field;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dev.amazonFakeTask.validators.Violation;

public class ValidatorEngine {

  private static final Map<Class<? extends ConstrainValidatorI<?>>, ConstrainValidatorI<?>> validatorCache = new ConcurrentHashMap<>();

  public static List<Violation> validate(Object target) {
    List<Violation> violations = new ArrayList<>();

    if (target == null) {
      violations.add(new Violation("root", null, "The object a validate is null"));
      return violations;
    }

    Field[] fields = target.getClass().getDeclaredFields();

    for (Field field : fields) {
      field.setAccessible(true);

      try {
        Object value = field.get(target);

        for (Annotation annotation : field.getAnnotations()) {
          Class<? extends Annotation> annotationType = annotation.annotationType();

          if (annotationType.isAnnotationPresent(Constraint.class)) {
            Constraint constraint = annotationType.getAnnotation(Constraint.class);
            Class<? extends ConstrainValidatorI<?>> validatorClass = constraint.validatedBy();

            ConstrainValidatorI<Annotation> validator = getValidatorInstance(validatorClass);

            if (!validator.isValid(annotation, value)) {
              violations.add(new Violation(field.getName(), value, validator.getMessage(annotation)));
            }
          }
        }

      } catch (IllegalAccessException e) {
        violations.add(new Violation(field.getName(), null, "unaccesible field"));
      }

    }

    return violations;

  }

  @SuppressWarnings("unchecked")
  private static ConstrainValidatorI<Annotation> getValidatorInstance(Class<? extends ConstrainValidatorI<?>> clazz) {
    return (ConstrainValidatorI<Annotation>) validatorCache.computeIfAbsent(clazz, key -> {
      try {
        return key.getDeclaredConstructor().newInstance();
      } catch (Exception e) {
        throw new RuntimeException("No se pudo instanciar el validador: " + key.getName(), e);
      }
    });
  }

}
