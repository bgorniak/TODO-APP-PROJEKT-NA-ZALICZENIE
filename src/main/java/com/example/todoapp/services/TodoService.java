package com.example.todoapp.services;

import com.example.todoapp.models.TodoItem;
import com.example.todoapp.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Optional<TodoItem> getById(Long id) {
        return todoRepository.findById(id);
    }

    public Iterable<TodoItem> getAll() {
        return todoRepository.findAll();
    }

    public TodoItem save(TodoItem todoItem) {
        if (todoItem.getId() == null) {
            todoItem.setCreatedAt(Instant.now());
        }
        todoItem.setUpdatedAt(Instant.now());
        return todoRepository.save(todoItem);
    }

    public void delete(TodoItem todoItem) {
        todoRepository.delete(todoItem);
    }

}
