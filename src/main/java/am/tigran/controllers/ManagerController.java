package am.tigran.controllers;

import am.tigran.dto.ReturnedTaskDto;
import am.tigran.dto.ReturnedUserDto;
import am.tigran.exception.UnknownUserException;
import am.tigran.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @PatchMapping(value = "/change/role/{id}")
    @PreAuthorize("hasAnyAuthority('change:role')")
    public ResponseEntity<?> changeRole(@PathVariable Integer id){
        try {
            managerService.changeRoleToManager(id);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UnknownUserException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/users")
    @PreAuthorize("hasAnyAuthority('change:role')")
    public ResponseEntity<Set<ReturnedUserDto>> getUsers(){
        Set<ReturnedUserDto> users = managerService.getUsers();
        if(users==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @GetMapping(value = "/tasks")
    @PreAuthorize("hasAnyAuthority('change:role')")
    public ResponseEntity<Set<ReturnedTaskDto>> getTasks(){
        Set<ReturnedTaskDto> users = managerService.getTasks();
        if(users==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

}
