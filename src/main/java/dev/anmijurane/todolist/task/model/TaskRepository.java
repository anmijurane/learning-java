package dev.anmijurane.todolist.task.model;

import java.util.List;

import dev.anmijurane.todolist.task.percistence.TaskPercistence;

public class TaskRepository {

  private List<Task> tasks;

  public TaskRepository() {
    this.tasks = TaskPercistence.loadTasks();
  }

  public List<Task> findAll() {
    return tasks;
  }

  public Task findById(Integer id) {
    for (Task task : tasks) {
      if (task.getId() == id) {
        return task;
      }
    }
    return null;
  }

  public void save(Task task) {
    tasks.add(task);
    percistAllTasks();
  }

  public void update(Integer id, Task task) {
    for (Task currentTask : tasks) {
      if (currentTask.getId() == id) {
        currentTask.setTitle(task.getTitle());
        currentTask.setDescription(task.getDescription());
        currentTask.setCompleted(task.isCompleted());
        break;
      }
    }
    percistAllTasks();
  }

  public void toggleChecked(Integer id, Boolean forceComplete) {
    for (Task currentTask : tasks) {
      if (currentTask.getId() == id) {
        currentTask.setCompleted(forceComplete != null ? forceComplete : !currentTask.isCompleted());
        break;
      }
    }
    percistAllTasks();
  }

  public void delete(Integer id) {
    Task task = findById(id);
    if (task != null) {
      tasks.remove(task);
    }
    percistAllTasks();
  }

  public void delete(Task task) {
    tasks.forEach((currentTask) -> {
      if (currentTask.getId() == task.getId()) {
        tasks.remove(currentTask);
      }
    });
    percistAllTasks();
  }

  public List<Task> getTasks() {
    return tasks;
  }

  private void percistAllTasks() {
    TaskPercistence.saveTasks(tasks);
  }

}
