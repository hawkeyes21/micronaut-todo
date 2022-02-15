package com.incubyte;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceShould {
    private Todo todo;

    @Mock
    private TodoRepository todoRepository;

    @BeforeEach
    public void init() {
        todo = new Todo("Remember eggs", Status.OPEN);
    }

    @Test
    void save_todo_to_persist_in_repository() {
        //Arrange
        TodoService todoService = new TodoService(todoRepository);
        //Act
        todoService.save(todo);
        //Assert
        Todo todoResponse = verify(todoRepository).save(todo);
    }

    @Test
    void find_todos_by_status() {
        //Arrange
        TodoService todoService = new TodoService(todoRepository);
        //Act
        todoService.getTodos(Status.OPEN);
        //Assert
        verify(todoRepository).findByStatusOrderById(Status.OPEN);
    }

    @Test
    void update_todo_status_to_close_by_todo_id() {
        TodoService todosService = new TodoService(todoRepository);
        Todo todo = new Todo("Title", Status.OPEN);
        todo.setId(1L);
        todosService.close(todo);

        verify(todoRepository).update(todo);
    }
}
