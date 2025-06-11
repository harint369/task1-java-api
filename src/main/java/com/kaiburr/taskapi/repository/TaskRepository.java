package com.kaiburr.taskapi.repository;

import com.kaiburr.taskapi.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * TaskRepository provides methods to perform database operations on Task objects.
 * It extends MongoRepository to get basic CRUD methods automatically.
 */
public interface TaskRepository extends MongoRepository<Task, String> {

    /**
     * Finds tasks where the name contains the given string (case-insensitive).
     * Example: searching "hello" will match "Print Hello", "sayHELLO", etc.
     *
     * @param name part of the task name to search for
     * @return list of matching Task objects
     */
    List<Task> findByNameContainingIgnoreCase(String name);
}

