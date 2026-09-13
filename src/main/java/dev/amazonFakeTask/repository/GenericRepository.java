package dev.amazonFakeTask.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import dev.amazonFakeTask.model.Identifiable;

public class GenericRepository<T extends Identifiable<ID>, ID> {

  private List<T> elements;

  public GenericRepository() {
    this.elements = new ArrayList<T>();
  }

  public void add(T el) {
    if (findById(el.getId()).isPresent()) {
      System.out.println("No se agrego, por que el ID es el mismo");
    }
    elements.add(el);
  }

  public void remove(ID id) {
    this.elements.removeIf(el -> Objects.equals(el.getId(), id));
  }

  public void update(T obj) {
    IntStream
        .range(0, elements.size())
        .forEach(idx -> elements.set(idx, obj));
  }

  public Optional<T> findById(ID id) {
    return elements
        .stream()
        .filter(el -> el.getId() == id)
        .findFirst();
  }

  public List<T> getAll() {
    return elements;
  }

}
