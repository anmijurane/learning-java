package dev.amazonFakeTask.validators;

public record Violation(
    String fieldName,
    Object invalidValue,
    String message) {

  @Override
  public final String toString() {
    return String.format("Campo '%s' (valor: %s): %s ", fieldName, invalidValue, message);
  }
}
