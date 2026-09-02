package dev.anmijurane.todolist.task.percistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dev.anmijurane.todolist.task.model.Task;

public class TaskPercistence {

  private final static String FILE_PATH_JSON = "tasks.json";

  private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

  public static void saveTasks(List<Task> tasks) {
    try (Writer writer = new FileWriter(FILE_PATH_JSON)) {
      gson.toJson(tasks, writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static List<Task> loadTasks() {

    File file = new File(FILE_PATH_JSON);

    if (!file.exists()) {
      return new ArrayList<>();
    }

    try (Reader reader = new FileReader(FILE_PATH_JSON)) {
      return gson.fromJson(reader, new TypeToken<List<Task>>() {
      }.getType());
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

}
