package com.incubyte;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.List;

@Controller("/todos")
public class TodosController {
  @Inject private TodoService todoService;

  public TodosController(TodoService todosService) {
    this.todoService = todosService;
  }

  @Post
  public MutableHttpResponse<Todo> save(@Body Todo todo) {
    if (todo.getTitle() == null || todo.getTitle().isEmpty()) {
      return HttpResponse.badRequest(todo);
    }
    return HttpResponse.ok(todoService.save(todo));
  }

  @Get("/open")
  public List<Todo> open() {
    return todoService.getTodos(Status.OPEN);
  }

  @Post("/{id}")
  public Todo close(@QueryValue String id) {
    return todoService.close(id);
  }
}
