package dev.anmijurane.FunctionalPredicate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Modelo que representa a un Usuario en el sistema.
 * Contiene información de identidad, rol, departamento y estado de la cuenta.
 */
@Getter
@ToString
@RequiredArgsConstructor
public class User {
  private final String id;
  private final String name;
  private final String role;          // "ADMIN", "EDITOR", "VIEWER"
  private final String department;    // "FINANCE", "IT", "HR"
  private final boolean isActive;     // true si la cuenta está activa
}
