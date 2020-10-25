package am.tigran.repository;

import am.tigran.models.Task;
import am.tigran.models.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

    List<Task> findByTaskStatus(TaskStatus taskStatus);
    List<Task> findByCreationDate(Date date);
}
