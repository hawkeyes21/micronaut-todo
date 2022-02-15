package com.incubyte;

import javax.persistence.*;

@Entity
@Table(name = "todos")
public class Todo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String title;
  Status status;

  public Todo() {}

  public Todo(String title, Status status) {
    this.title = title;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
