package dev.anmijurane.FunctionalPredicate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Modelo que representa un Recurso/Documento en el sistema.
 * Contiene información de propiedad, departamento de pertenencia y banderas de seguridad.
 */
@Getter
@ToString
@RequiredArgsConstructor
public class Document {
  private final String id;
  private final String title;
  private final String ownerId;          // ID del usuario que creó el documento
  private final String department;       // Departamento al que pertenece ("FINANCE", "IT", etc.)
  private final boolean isConfidential;  // true si requiere permisos especiales de departamento
  private final boolean isLocked;        // true si el documento está bloqueado para edición
}
