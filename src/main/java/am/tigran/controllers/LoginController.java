package am.tigran.controllers;

import am.tigran.dto.ReturnedUserDto;
import am.tigran.dto.UserDto;
import am.tigran.exception.UnknownUserException;
import am.tigran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<ReturnedUserDto> loginUser(@RequestBody UserDto userDto){
        try {
            ReturnedUserDto returnedUserDto =userService.loginUser(userDto);
            return  new ResponseEntity<>(returnedUserDto, HttpStatus.OK);
        } catch (UnknownUserException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
