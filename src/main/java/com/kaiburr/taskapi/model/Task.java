package com.kaiburr.taskapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

/**
 * Task class represents a task that will be stored in MongoDB.
 * Each task has an ID, name, owner, a shell command to run,
 * and a list of all its previous executions.
 */
@Data                   // Automatically generates getter/setter methods
@NoArgsConstructor      // Generates a no-argument constructor
@AllArgsConstructor     // Generates a constructor with all fields
@Document("tasks")      // Maps this class to a MongoDB collection named "tasks"
public class Task {

    @Id
    private String id;  // Unique identifier for the task (used by MongoDB)

    private String name;  // Name of the task (e.g., "Print Hello")

    private String owner;  // Name of the person who created the task

    private String command;  // Shell command to be executed (e.g., "echo Hello")

    private List<TaskExecution> taskExecutions = new ArrayList<>();
    // Stores all the previous executions of this task
    // Each execution contains start time, end time, and command output

    // NOTE: taskExecutions is initialized with an empty list to avoid null pointer errors
}
