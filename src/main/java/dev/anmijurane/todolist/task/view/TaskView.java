package dev.anmijurane.todolist.task.view;

import java.util.Scanner;

import dev.anmijurane.todolist.task.controller.TaskController;

public class TaskView {

  private final TaskController taskController;
  private final Scanner scanner;

  public TaskView(TaskController taskController, Scanner scanner) {
    this.taskController = taskController;
    this.scanner = new Scanner(System.in);
  }

  public void showMenu() {
    int option = 0;

    while (option != 6) {
      System.out.println("=== Todo List ===");
      System.out.println("1. Agregar tarea");
      System.out.println("2. Eliminar tarea");
      System.out.println("3. Editar tarea");
      System.out.println("4. Mostrar tareas");
      System.out.println("5. Alternar Estado de una Tarea");
      System.out.println("6. Salir");
      System.out.println("Digite una opción: ");
      option = scanner.nextInt();
      System.out.println("---------------------------------");

      switch (option) {
        case 1:
          addTaskView();
          break;
        case 2:
          removeTaskView();
          break;
        case 3:
          editTaskView();
          break;
        case 4:
          showTaskView();
          break;
        case 5:
          toggleTaskView();
          break;
        case 6:
          System.out.println("Saindo...");
          System.exit(0);
          break;
        default:
          System.out.println("Opción inválida");
      }
    }

  }

  public void addTaskView() {
    System.out.println("=== Agregar Tarea ===");
    System.out.println("ID de tarea: ");
    int id = scanner.nextInt();
    scanner.nextLine();
    System.out.println("título de tarea: ");
    String title = scanner.nextLine();
    System.out.println("descripción de tarea: ");
    String description = scanner.nextLine();
    taskController.addTask(id, title, description, false);
    System.out.println("Se agrego la tarea");
    System.out.println(taskController.showTaskById(id));
  }

  public void removeTaskView() {
    System.out.println("=== Remover tarea ===");
    System.out.println("ID de tarea: ");
    int id = scanner.nextInt();
    taskController.removeTask(id);
  }

  public void editTaskView() {
    System.out.println("=== Editar tarea ===");
    System.out.println("ID de tarea: ");
    int id = scanner.nextInt();
    System.out.println("novo título de tarea: ");
    String title = scanner.next();
    System.out.println("nova descrição de tarea: ");
    String description = scanner.next();
    System.out.println("novo status de tarea (true o false): ");
    boolean completed = scanner.nextBoolean();
    taskController.editTask(id, title, description, completed);
  }

  public void showTaskView() {
    System.out.println("=== Mostrar tareas ===");
    taskController.showTasks();
  }

  public void toggleTaskView() {
    System.out.println("=== Alternar Status de una Tarea ===");
    System.out.println("ID de la tarea: ");
    int id = scanner.nextInt();
    taskController.toggleTaskChecked(id, null);
  }

}
