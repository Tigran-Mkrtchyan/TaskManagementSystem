package am.tigran.service;

import am.tigran.dto.ChangeStatusInTaskDto;
import am.tigran.dto.ChangeUserInTaskDto;
import am.tigran.dto.ReturnedTaskDto;
import am.tigran.dto.TaskDto;
import am.tigran.exception.UnknownStatusException;
import am.tigran.exception.UnknownTaskException;
import am.tigran.exception.UnknownUserException;
import am.tigran.models.Task;
import am.tigran.models.TaskStatus;
import am.tigran.models.User;
import am.tigran.repository.TaskRepository;
import am.tigran.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

@Service
@Transactional
public class TaskService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Integer addTask(TaskDto taskDto, Integer userId) throws UnknownUserException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UnknownUserException("Incorrect email or password")
        );
        Task task = new Task();
        task.setUser(user);
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        task.setCreationDate(date);
        task.setUpdateDate(date);
        task.setDescription(taskDto.getDescription());
        task.setTaskName(taskDto.getTaskName());
        task.setTaskStatus(TaskStatus.NEW_TASK);
        taskRepository.save(task);
        return task.getId();
    }

    public Integer changeUser(ChangeUserInTaskDto userInTaskDto) throws UnknownUserException, UnknownTaskException {
        User user = userRepository.findById(userInTaskDto.getUserId()).orElseThrow(
                () -> new UnknownUserException("Incorrect user id")
        );
        Task task = taskRepository.findById(userInTaskDto.getTaskId()).orElseThrow(
                () -> new UnknownTaskException("incorrect task id")
        );

        task.add(user);
        return task.getId();
    }

    public Integer changeStatus(ChangeStatusInTaskDto changeStatusInTaskDto) throws UnknownTaskException, UnknownStatusException {
        Task task = taskRepository.findById(changeStatusInTaskDto.getTaskId()).orElseThrow(
                () -> new UnknownTaskException("incorrect task id")
        );
        TaskStatus taskStatus = TaskStatus.fromString(changeStatusInTaskDto.getStatus());
        task.setTaskStatus(taskStatus);
        task.setUpdateDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        taskRepository.save(task);
        return task.getId();


    }

    public void deleteTask(Integer id) throws UnknownTaskException {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new UnknownTaskException("incorrect task id")
        );
        taskRepository.delete(task);
    }

    public Set<ReturnedTaskDto> getTaskByUser(Integer id) throws UnknownUserException {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UnknownUserException("Incorrect user id")
        );
        Set<ReturnedTaskDto> returnedTasks = new HashSet<>();
        Set<Task> tasks = user.getTasks();
        for (Task task : tasks) {
            ReturnedTaskDto returnedTask = new ReturnedTaskDto();
            returnedTask.setTaskId(task.getId());
            returnedTask.setTaskName(task.getTaskName());
            returnedTask.setCreationDate(task.getCreationDate().toString());
            returnedTask.setUpdateDate(task.getUpdateDate().toString());
            returnedTask.setDescription(task.getDescription());
            returnedTask.setUserName(user.getName());
            returnedTasks.add(returnedTask);
        }
        return returnedTasks;
    }

    public Set<ReturnedTaskDto> getTaskByStatus(String status) throws UnknownStatusException {

        Set<ReturnedTaskDto> returnedTasks = new HashSet<>();
        TaskStatus taskStatus = TaskStatus.fromString(status);
        List<Task> tasks = taskRepository.findByTaskStatus(taskStatus);
        for (Task task : tasks) {
            ReturnedTaskDto returnedTask = new ReturnedTaskDto();
            returnedTask.setTaskId(task.getId());
            returnedTask.setTaskName(task.getTaskName());
            returnedTask.setCreationDate(task.getCreationDate().toString());
            returnedTask.setUpdateDate(task.getUpdateDate().toString());
            returnedTask.setDescription(task.getDescription());
            returnedTask.setUserName(task.getUser().getName());
            returnedTasks.add(returnedTask);
        }
        return returnedTasks;
    }

    public Set<ReturnedTaskDto> getTaskByDate(String date) {
        Date creationDate = Date.valueOf(date);
        Set<ReturnedTaskDto> returnedTasks = new HashSet<>();
        List<Task> tasks = taskRepository.findByCreationDate(creationDate);
        for (Task task : tasks) {
            ReturnedTaskDto returnedTask = new ReturnedTaskDto();
            returnedTask.setTaskId(task.getId());
            returnedTask.setTaskName(task.getTaskName());
            returnedTask.setCreationDate(task.getCreationDate().toString());
            returnedTask.setUpdateDate(task.getUpdateDate().toString());
            returnedTask.setDescription(task.getDescription());
            returnedTask.setUserName(task.getUser().getName());
            returnedTasks.add(returnedTask);
        }
        return returnedTasks;
    }
}
