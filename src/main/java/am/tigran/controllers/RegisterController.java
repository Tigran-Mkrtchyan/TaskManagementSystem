package am.tigran.controllers;

import am.tigran.dto.RegisterUserDto;
import am.tigran.dto.ReturnedUserDto;
import am.tigran.exception.EmailAlreadyExistException;
import am.tigran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<ReturnedUserDto> registerUser(@RequestBody RegisterUserDto registerUserDto){
        try {
            ReturnedUserDto returnedUserDto = userService.registerUser(registerUserDto);
            return new ResponseEntity<>(returnedUserDto,HttpStatus.CREATED);
        } catch (EmailAlreadyExistException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
