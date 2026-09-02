package dev.anmijurane.todolist.task.controller;

import java.util.List;

import dev.anmijurane.todolist.task.exceptions.TaskValidationException;
import dev.anmijurane.todolist.task.model.Task;
import dev.anmijurane.todolist.task.model.TaskRepository;

public class TaskController {

  private final TaskRepository taskRepository;

  public TaskController(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public void addTask(Integer id, String title, String description, boolean completed) {

    if (id == null || title == null || description == null) {
      throw new TaskValidationException("Id, title and description are required");
    }

    Task newTask = new Task(id, title, description, completed);
    this.taskRepository.save(newTask);
  }

  public void removeTask(Integer id) {

    if (id == null) {
      throw new TaskValidationException("Id is required");
    }

    this.taskRepository.delete(id);
  }

  public void editTask(Integer id, String title, String description, boolean completed) {

    if (id == null) {
      throw new TaskValidationException("Id is required");
    }
    Task updatedTask = new Task(id, title, description, completed);
    this.taskRepository.update(id, updatedTask);
  }

  public void showTasks() {
    List<Task> tasks = this.taskRepository.getTasks();

    if (tasks.isEmpty()) {
      throw new TaskValidationException("Task is Empty");
    }
    tasks.forEach(task -> System.out.println(task.toString()));
  }

  public String showTaskById(Integer id) {
    List<Task> tasks = this.taskRepository.getTasks();

    if (tasks.isEmpty()) {
      throw new TaskValidationException("Task is Empty");
    }
    Task task = this.taskRepository.findById(id);
    return task.toString();
  }

  public void toggleTaskChecked(Integer id, Boolean forceComplete) {
    this.taskRepository.toggleChecked(id, forceComplete);
  }

}
