package com.incubyte;

import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Singleton
public class TodoService {
  private final TodoRepository todoRepository;

  public TodoService(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Transactional
  public Todo save(Todo todo) {
    return todoRepository.save(todo);
  }

  public List<Todo> getTodos(Status status) {
    return todoRepository.findByStatusOrderById(status);
  }

  public Todo close(String id) {
    Optional<Todo> todo = todoRepository.findById(Long.valueOf(id));
    return todoRepository.update(todo.get());
  }
}
