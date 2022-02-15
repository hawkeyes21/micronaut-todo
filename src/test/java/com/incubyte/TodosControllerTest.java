package com.incubyte;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import liquibase.pro.packaged.S;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class TodosControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    Map<String, String> todo = new HashMap<>();
    Map<String, String> todo2 = new HashMap<>();

    @BeforeEach
    public void init() {
        todo.put("title", "Go for a walk");
        todo.put("status", "OPEN");

        todo2.put("title", "Read a book");
        todo2.put("status", "OPEN");
    }

    @Test
    void should_save_todos() {
        // Arrange

        // ACT
        Map<String, String> response =
                client.toBlocking().retrieve(HttpRequest.POST("/todos", todo), Argument.mapOf(String.class, String.class));
        // Assert
        assertThat(response.get("id")).isNotNull();
        assertThat(response.get("id")).isNotEmpty();
        assertThat(response.get("title")).isEqualTo("Go for a walk");
        assertThat(response.get("status")).isEqualTo("OPEN");

        response =
                client.toBlocking().retrieve(HttpRequest.POST("/todos", todo2), Argument.mapOf(String.class, String.class));
        // Assert
        assertThat(response.get("id")).isNotNull();
        assertThat(response.get("id")).isNotEmpty();
        assertThat(response.get("title")).isEqualTo("Read a book");
        assertThat(response.get("status")).isEqualTo("OPEN");

        List<Map> todos =
                client.toBlocking().retrieve(HttpRequest.GET("/todos/open"), Argument.listOf(Map.class));

        assertThat(todos.size()).isEqualTo(2);
    }

    @Test
    void should_not_save_todo_if_title_is_empty() {
        // Arrange
        Map<String, String> todo = new HashMap<>();
        todo.put("title", "");
        todo.put("status", "OPEN");

        // ACT
        Assertions.assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().retrieve(HttpRequest.POST("/todos", todo), Argument.of(HttpResponse.class)));
    }

    @Test
    void should_close_an_open_todo() {
        // ACT
        Map<String, String> response =
                client.toBlocking().retrieve(HttpRequest.POST("/todos", todo), Argument.mapOf(String.class, String.class));

        // Assert
        assertThat(response.get("id")).isNotNull();
        assertThat(response.get("id")).isNotEmpty();
        assertThat(response.get("title")).isEqualTo("Go for a walk");
        assertThat(response.get("status")).isEqualTo("OPEN");

        Map<String, String> closedTodo = new HashMap<>();
        closedTodo.put("id", response.get("id"));
        closedTodo.put("title", response.get("title"));
        closedTodo.put("status", "CLOSED");

        Map<String, String> newTodo =
                client.toBlocking().retrieve(HttpRequest.PATCH("/todos/", closedTodo), Argument.mapOf(String.class, String.class));

        assertThat(newTodo.get("id")).isNotNull();
        assertThat(newTodo.get("id")).isNotEmpty();
        assertThat(newTodo.get("title")).isEqualTo("Go for a walk");
        assertThat(newTodo.get("status")).isEqualTo("CLOSED");
    }

}