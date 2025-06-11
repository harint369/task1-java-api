package com.kaiburr.taskapi.controller;

import com.kaiburr.taskapi.model.Task;
import com.kaiburr.taskapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for handling HTTP requests related to Task operations.
 * Supports creating, reading, deleting, searching, and executing tasks.
 */
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * GET /tasks
     * If 'id' parameter is provided, return the task with that ID.
     * If not, return all tasks.
     *
     * @param id Optional task ID
     * @return Single task or list of all tasks
     */
    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(required = false) String id) {
        if (id != null) {
            return taskService.getTaskById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    /**
     * POST /tasks
     * Create a new task with the given data.
     *
     * @param task Task object from request body
     * @return Created Task with HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task created = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * DELETE /tasks/{id}
     * Deletes the task with the given ID.
     *
     * @param id Task ID
     * @return HTTP 204 No Content if deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /tasks/search?name=xyz
     * Find tasks whose names contain the given string (case-insensitive).
     *
     * @param name Part of the task name to search for
     * @return Matching tasks or 404 if none found
     */
    @GetMapping("/search")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        List<Task> tasks = taskService.findTasksByName(name);
        return tasks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(tasks);
    }

    /**
     * PUT /tasks/{id}/execute
     * Executes the command of the task with the given ID and stores execution details.
     *
     * @param id Task ID
     * @return Updated task object with new execution details, or error if failed
     */
    @PutMapping("/{id}/execute")
    public ResponseEntity<?> executeTask(@PathVariable String id) {
        try {
            Task updatedTask = taskService.runTaskCommand(id);
            return ResponseEntity.ok(updatedTask);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Execution error: " + e.getMessage());
        }
    }
}
