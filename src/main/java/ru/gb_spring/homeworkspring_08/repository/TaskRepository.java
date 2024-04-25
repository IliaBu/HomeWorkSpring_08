package ru.gb_spring.homeworkspring_08.repository;


import org.springframework.data.repository.CrudRepository;
import ru.gb_spring.homeworkspring_08.model.Task;
import ru.gb_spring.homeworkspring_08.model.TaskStatus;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    /**
     * получить список задач по их статусу
     */
    List<Task> findByStatus(TaskStatus taskStatus);
}
