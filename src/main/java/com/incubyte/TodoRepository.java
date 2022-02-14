package com.incubyte;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findByStatusOrderById(Status status);
    Optional<Todo> findById(Long id);
}
