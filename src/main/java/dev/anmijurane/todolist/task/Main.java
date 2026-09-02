package dev.anmijurane.todolist.task;

import dev.anmijurane.todolist.task.controller.TaskController;
import dev.anmijurane.todolist.task.model.TaskRepository;
import dev.anmijurane.todolist.task.view.TaskView;

public class Main {
  public static void main(String[] args) {
    TaskRepository taskRepository = new TaskRepository();
    TaskController taskController = new TaskController(taskRepository);
    TaskView taskView = new TaskView(taskController, null);
    taskView.showMenu();
  }
}
