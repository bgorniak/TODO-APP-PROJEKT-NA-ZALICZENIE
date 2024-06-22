package com.example.todoapp.controllers;

import com.example.todoapp.models.TodoItem;
import com.example.todoapp.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
public class TodoRestController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public ResponseEntity<Iterable<TodoItem>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getTodoById(@PathVariable Long id) {
        Optional<TodoItem> todoItem = todoService.getById(id);
        return todoItem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TodoItem> createTodoItem(@RequestBody TodoItem todoItem) {
        TodoItem savedTodoItem = todoService.save(todoItem);
        return ResponseEntity.ok(savedTodoItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoItem> updateTodoItem(@PathVariable Long id, @RequestBody TodoItem updatedTodoItem) {
        Optional<TodoItem> optionalTodoItem = todoService.getById(id);
        if (optionalTodoItem.isPresent()) {
            TodoItem existingTodoItem = optionalTodoItem.get();
            existingTodoItem.setTask(updatedTodoItem.getTask());
            existingTodoItem.setIsComplete(updatedTodoItem.getIsComplete());
            TodoItem savedTodoItem = todoService.save(existingTodoItem);
            return ResponseEntity.ok(savedTodoItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable Long id) {
        Optional<TodoItem> optionalTodoItem = todoService.getById(id);
        if (optionalTodoItem.isPresent()) {
            todoService.delete(optionalTodoItem.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
