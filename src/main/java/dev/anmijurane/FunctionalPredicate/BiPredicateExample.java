package dev.anmijurane.FunctionalPredicate;

import java.util.function.BiPredicate;

/**
 * ============================================================================
 * EJERCICIO 2: Motor de Autorización y Permisos RBAC usando BiPredicate<T, U>
 * ============================================================================
 *
 * ¿QUÉ ES BiPredicate<T, U>?
 * Es una interfaz funcional estándar de Java (en java.util.function) que:
 * - Recibe DOS argumentos de tipos potencialmente distintos (T y U).
 * - Evalúa una condición lógica sobre ellos.
 * - Retorna un valor booleano primitivo (true o false).
 * - Firma abstracta: boolean test(T t, U u);
 *
 * ¿POR QUÉ USARLO EN SEGURIDAD Y PERMISOS?
 * Las decisiones de autorización en el mundo real (como en Spring Security,
 * OAuth2 o AWS IAM) casi nunca evalúan un objeto de forma aislada. Siempre
 * dependen de la RELACIÓN entre dos entidades:
 *      (Sujeto / Usuario)  <--->  (Objeto / Recurso)
 */
public class BiPredicateExample {

  public static void main(String[] args) {
    BiPredicateExample service = new BiPredicateExample();
    service.runExercise2();
  }

  public void runExercise2() {
    System.out.println("=================================================================");
    System.out.println("      SISTEMA DE CONTROL DE ACCESO A DOCUMENTOS (RBAC)           ");
    System.out.println("=================================================================\n");

    // ------------------------------------------------------------------------
    // 1. DEFINICIÓN DE REGLAS ATÓMICAS (BiPredicates independientes)
    // ------------------------------------------------------------------------

    // Regla 1: El usuario tiene rol "ADMIN" y su cuenta está activa.
    // (Nota: Aunque ignora el segundo parámetro 'doc', debe coincidir con la firma BiPredicate)
    BiPredicate<User, Document> isAdmin = (user, doc) ->
        user.isActive() && "ADMIN".equalsIgnoreCase(user.getRole());

    // Regla 2: El usuario es el propietario directo del documento.
    BiPredicate<User, Document> isOwner = (user, doc) ->
        user.isActive() && user.getId().equals(doc.getOwnerId());

    // Regla 3: El usuario y el documento pertenecen al mismo departamento.
    BiPredicate<User, Document> isSameDepartment = (user, doc) ->
        user.getDepartment().equalsIgnoreCase(doc.getDepartment());

    // Regla 4: El documento está desbloqueado para permitir edición.
    BiPredicate<User, Document> isDocumentEditable = (user, doc) ->
        !doc.isLocked();

    // Regla 5: Si el documento es confidencial, requiere pertenecer al mismo departamento.
    BiPredicate<User, Document> meetsConfidentiality = (user, doc) ->
        !doc.isConfidential() || user.getDepartment().equalsIgnoreCase(doc.getDepartment());


    // ------------------------------------------------------------------------
    // 2. COMPOSICIÓN DE POLÍTICAS DE ACCESO (and, or, negate)
    // ------------------------------------------------------------------------

    /*
     * POLÍTICA DE LECTURA (canReadDocument):
     * Un usuario puede LEER un documento si:
     * - Es ADMIN activo
     *   O BIEN
     * - Cumple con la regla de confidencialidad (mismo depto si es confidencial).
     */
    BiPredicate<User, Document> canReadDocument = isAdmin.or(meetsConfidentiality);

    /*
     * POLÍTICA DE EDICIÓN (canEditDocument):
     * Un usuario puede EDITAR un documento si:
     * - Es ADMIN activo (los administradores siempre pueden editar).
     *   O BIEN
     * - (Es el PROPIETARIO AND Pertenece al MISMO departamento AND el documento NO está bloqueado).
     */
    BiPredicate<User, Document> isAuthorizedOwner = isOwner
        .and(isSameDepartment)
        .and(isDocumentEditable);

    BiPredicate<User, Document> canEditDocument = isAdmin.or(isAuthorizedOwner);


    // ------------------------------------------------------------------------
    // 3. DATOS DE PRUEBA (Usuarios y Documentos)
    // ------------------------------------------------------------------------

    User adminUser     = new User("U1", "Carlos Admin", "ADMIN", "IT", true);
    User financeUser   = new User("U2", "Ana Finanzas", "EDITOR", "FINANCE", true);
    User hrUser        = new User("U3", "Luis RRHH", "EDITOR", "HR", true);
    User inactiveOwner = new User("U4", "Mario Bloqueado", "EDITOR", "FINANCE", false);

    Document budgetDoc     = new Document("D1", "Presupuesto 2026", "U2", "FINANCE", true, false);
    Document lockedDoc     = new Document("D2", "Auditoría Contable Final", "U2", "FINANCE", true, true);
    Document publicItGuide = new Document("D3", "Guía VPN Remota", "U1", "IT", false, false);


    // ------------------------------------------------------------------------
    // 4. EJECUCIÓN Y EVALUACIÓN DE CASOS DE USO
    // ------------------------------------------------------------------------

    // Caso 1: El propietario (Ana) intenta editar su documento abierto en Finanzas
    evaluateAccess("Caso 1 [Propietario edita su doc]", financeUser, budgetDoc, "EDITAR", canEditDocument);

    // Caso 2: El propietario (Ana) intenta editar su documento pero está BLOQUEADO (Locked)
    evaluateAccess("Caso 2 [Propietario edita doc bloqueado]", financeUser, lockedDoc, "EDITAR", canEditDocument);

    // Caso 3: Un usuario de otro departamento (Luis de RRHH) intenta leer doc confidencial de Finanzas
    evaluateAccess("Caso 3 [Otro departamento lee confidencial]", hrUser, budgetDoc, "LEER", canReadDocument);

    // Caso 4: Un usuario de otro departamento (Luis de RRHH) intenta leer doc público de IT
    evaluateAccess("Caso 4 [Otro departamento lee documento público]", hrUser, publicItGuide, "LEER", canReadDocument);

    // Caso 5: El Administrador (Carlos) intenta editar cualquier documento
    evaluateAccess("Caso 5 [Admin edita documento bloqueado de finanzas]", adminUser, lockedDoc, "EDITAR", canEditDocument);

    // Caso 6: Usuario inactivo intenta editar su propio documento
    evaluateAccess("Caso 6 [Usuario inactivo intenta editar]", inactiveOwner, budgetDoc, "EDITAR", canEditDocument);
  }

  /**
   * Método auxiliar que centraliza la invocación de la política mediante test().
   *
   * @param testCase Nombre descriptivo del escenario evaluado.
   * @param user Sujeto que solicita la acción.
   * @param doc Recurso sobre el que se solicita la acción.
   * @param actionName Nombre de la acción ("LEER", "EDITAR").
   * @param policy La regla funcional BiPredicate a evaluar.
   */
  public void evaluateAccess(String testCase, User user, Document doc, String actionName, BiPredicate<User, Document> policy) {
    // Invocación del método funcional test(T, U)
    boolean isAllowed = policy.test(user, doc);

    String status = isAllowed ? "✅ PERMITIDO" : "❌ DENEGADO";
    System.out.printf("%-45s | Usuario: %-15s | Doc: %-25s | Acción: %-6s -> %s%n",
        testCase, user.getName(), doc.getTitle(), actionName, status);
  }
}
