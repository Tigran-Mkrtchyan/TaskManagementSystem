package am.tigran.controllers;

import am.tigran.dto.*;
import am.tigran.exception.UnknownStatusException;
import am.tigran.exception.UnknownTaskException;
import am.tigran.exception.UnknownUserException;
import am.tigran.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping(value = "/task")
public class TaskController {
    @Autowired
   private TaskService taskService;

    @PostMapping(value = "/add/{id}")
    @PreAuthorize("hasAnyAuthority('create:task')")
    public ResponseEntity<Integer> addTask(@RequestBody TaskDto taskDto, @PathVariable Integer id){
        try {
            Integer taskId = taskService.addTask(taskDto, id);
            return new ResponseEntity<>(taskId,HttpStatus.CREATED);
        } catch (UnknownUserException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping(value = "/change/user")
    @PreAuthorize("hasAnyAuthority('change:user')")
    public ResponseEntity<Integer> changeUser(@RequestBody ChangeUserInTaskDto changeUserInTaskDto) {
        try {
            Integer taskId = taskService.changeUser(changeUserInTaskDto);
            return new ResponseEntity<>(taskId,HttpStatus.CREATED);
        } catch (UnknownUserException | UnknownTaskException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping(value = "/change/status")
    @PreAuthorize("hasAnyAuthority('change:status')")
    public ResponseEntity<Integer> changeUser(@RequestBody ChangeStatusInTaskDto changeStatusInTaskDto) {
        try {
            Integer taskId = taskService.changeStatus(changeStatusInTaskDto);
            return new ResponseEntity<>(taskId,HttpStatus.CREATED);
        } catch (UnknownStatusException | UnknownTaskException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyAuthority('delete:task')")
    public ResponseEntity<?> deleteTask(@PathVariable Integer id){
        try {
            taskService.deleteTask(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (UnknownTaskException e) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/filter/user")
    @PreAuthorize("hasAnyAuthority('read:task')")
    public ResponseEntity<Set<ReturnedTaskDto>> filterTaskByUser(@RequestParam("user") Integer id){
        try {
            Set< ReturnedTaskDto> returnedTaskDto= taskService.getTaskByUser(id);
            if(returnedTaskDto == null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(returnedTaskDto,HttpStatus.OK);
        } catch (UnknownUserException e) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping(value = "/filter/status")
    @PreAuthorize("hasAnyAuthority('read:task')")
    public ResponseEntity<Set<ReturnedTaskDto>> filterTaskByStatus(@RequestParam("status") String status){
        try {
            Set< ReturnedTaskDto> returnedTaskDto= taskService.getTaskByStatus(status);
            if(returnedTaskDto == null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(returnedTaskDto,HttpStatus.OK);
        } catch (UnknownStatusException e) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/filter/date")
    @PreAuthorize("hasAnyAuthority('read:task')")
    public ResponseEntity<Set<ReturnedTaskDto>> filterTaskByCreationDate(@RequestParam("date") String date){

            Set<ReturnedTaskDto> returnedTaskDto = taskService.getTaskByDate(date);
            if (returnedTaskDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(returnedTaskDto, HttpStatus.OK);
        }

}
