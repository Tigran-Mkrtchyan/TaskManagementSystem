package am.tigran.service;

import am.tigran.dto.ReturnedTaskDto;
import am.tigran.dto.ReturnedUserDto;
import am.tigran.exception.UnknownUserException;
import am.tigran.models.Role;
import am.tigran.models.Task;
import am.tigran.models.TaskStatus;
import am.tigran.models.User;
import am.tigran.repository.TaskRepository;
import am.tigran.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ManagerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public void changeRoleToManager(Integer userId) throws UnknownUserException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UnknownUserException("Incorrect email or password")
        );
        user.setRole(Role.MANAGER);
        userRepository.save(user);

    }

    public Set<ReturnedUserDto> getUsers() {
        List<User> usersList = userRepository.findAll();
        Set<ReturnedUserDto> users = new HashSet();
        for (User user : usersList) {
            ReturnedUserDto returnedUserDto = new ReturnedUserDto();
            returnedUserDto.setName(user.getName());
            returnedUserDto.setUserId(user.getId());
            users.add(returnedUserDto);
        }
        return users;
    }

    public Set<ReturnedTaskDto> getTasks() {

        Set<ReturnedTaskDto> returnedTasks = new HashSet<>();
        List<Task> tasks = taskRepository.findAll();
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
