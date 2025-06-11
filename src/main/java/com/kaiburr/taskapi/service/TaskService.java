package com.kaiburr.taskapi.service;

import com.kaiburr.taskapi.model.Task;
import com.kaiburr.taskapi.model.TaskExecution;
import com.kaiburr.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing Task-related business logic.
 * Interacts with the repository and handles task execution.
 */
@Service
@RequiredArgsConstructor // Lombok generates constructor for the 'repository' field
public class TaskService {

    private final TaskRepository repository;

    /**
     * Fetches all tasks from the database.
     *
     * @return List of all Task objects
     */
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id The ID of the task
     * @return Optional containing the task if found, or empty otherwise
     */
    public Optional<Task> getTaskById(String id) {
        return repository.findById(id);
    }

    /**
     * Finds tasks with names that contain the given string (case-insensitive).
     *
     * @param name Substring to search for in task names
     * @return List of matching tasks
     */
    public List<Task> findTasksByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Creates a new task after validating its shell command.
     *
     * @param task Task object to be created
     * @return Saved Task object
     * @throws IllegalArgumentException if the command is unsafe
     */
    public Task createTask(Task task) {
        if (containsUnsafeCommand(task.getCommand())) {
            throw new IllegalArgumentException("Unsafe command is not allowed.");
        }
        return repository.save(task);
    }

    /**
     * Deletes a task by ID.
     *
     * @param id Task ID
     */
    public void deleteTask(String id) {
        repository.deleteById(id);
    }

    /**
     * Executes the shell command for a task by ID.
     * Stores execution details (start time, end time, and output).
     *
     * @param id ID of the task to execute
     * @return Updated Task object with new TaskExecution added
     * @throws Exception if task not found or command execution fails
     */
    public Task runTaskCommand(String id) throws Exception {
        // Find the task or throw error
        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Date start = new Date(); // Record start time

        // Set up process builder to execute the shell command
        ProcessBuilder pb = new ProcessBuilder("sh", "-c", task.getCommand());
        pb.redirectErrorStream(true); // Merge stdout and stderr

        Process process = pb.start();

        // Capture command output
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        process.waitFor(); // Wait for command to finish
        Date end = new Date(); // Record end time

        // Create and store execution result
        TaskExecution exec = new TaskExecution(start, end, output.toString());
        task.getTaskExecutions().add(exec);

        return repository.save(task); // Save updated task
    }

    /**
     * Checks if the command contains any dangerous keywords.
     *
     * @param command Shell command to validate
     * @return true if unsafe keyword is found, false otherwise
     */
    private boolean containsUnsafeCommand(String command) {
        String[] blacklist = {"rm", "shutdown", "reboot", "mkfs", "dd", "kill", ":", ">", "<", "2>", ">>"};
        for (String keyword : blacklist) {
            if (command.contains(keyword)) return true;
        }
        return false;
    }
}
