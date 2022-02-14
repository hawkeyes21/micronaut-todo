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
        todo.put("title", "Remember eggs");
        todo.put("status", "OPEN");

        todo2.put("title", "Remember milk");
        todo2.put("status", "OPEN");
    }

    @Test
    void should_save_todos() {
        // Arrange

        // ACT
        Map<String, String> response =
                client
                        .toBlocking()
                        .retrieve(HttpRequest.POST("/todos", todo), Argument.mapOf(String.class, String.class));
        // Assert
        assertThat(response.get("id")).isNotNull();
        assertThat(response.get("id")).isNotEmpty();
        assertThat(response.get("title")).isEqualTo("Remember eggs");
        assertThat(response.get("status")).isEqualTo("OPEN");

        response =
                client
                        .toBlocking()
                        .retrieve(HttpRequest.POST("/todos", todo2), Argument.mapOf(String.class, String.class));
        // Assert
        assertThat(response.get("id")).isNotNull();
        assertThat(response.get("id")).isNotEmpty();
        assertThat(response.get("title")).isEqualTo("Remember milk");
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

        Assertions.assertThrows(
                HttpClientResponseException.class,
                () ->
                        client
                                .toBlocking()
                                .retrieve(HttpRequest.POST("/todos", todo), Argument.of(HttpResponse.class)));

    }

    @Test
    void should_close_an_open_todo() {
        // Arrange
        Map<String, String> todo = new HashMap<>();
//        todo.put("id", "1");
        todo.put("title", "we're just updating todo");
        todo.put("status", "OPEN");
        // ACT

//    Assertions.assertThrows(
//            HttpClientResponseException.class,
//            () -> client
//                    .toBlocking()
//                    .retrieve(HttpRequest.POST("/todos/{id}", todo), Argument.of(HttpResponse.class)));

        Map<String, String> response =
                client
                        .toBlocking()
                        .retrieve(HttpRequest.POST("/todos", todo), Argument.mapOf(String.class, String.class));

        System.out.println(response);

        response.put("status", "CLOSED");

        Map<String, String> todos =
                client.toBlocking().retrieve(HttpRequest.POST("/todos/" + response.get("id"), null), Argument.mapOf(String.class, String.class));
//    assertThat(todos.get(0).getTitle()).isEqualTo("we're just updating todo");
//    assertThat(todos.get(0).getId()).isEqualTo("1");

    }

}
