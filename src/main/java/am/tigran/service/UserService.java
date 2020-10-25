package am.tigran.service;

import am.tigran.dto.RegisterUserDto;
import am.tigran.dto.ReturnedUserDto;
import am.tigran.dto.UserDto;
import am.tigran.exception.EmailAlreadyExistException;
import am.tigran.exception.UnknownUserException;
import am.tigran.models.Role;
import am.tigran.models.User;
import am.tigran.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ReturnedUserDto loginUser(UserDto userDto) throws UnknownUserException {
        User user = userRepository.findUserByEmail(userDto.getEmail()).orElseThrow(
                () -> new UnknownUserException("Incorrect email or password")
        );

        if (!passwordEncoder.matches(userDto.getPassword(),user.getPassword())){
            throw new UnknownUserException("email or password is incorrect");
        }
        ReturnedUserDto returnedUserDto = new ReturnedUserDto();
        returnedUserDto.setUserId(user.getId());
        returnedUserDto.setName(user.getName());
        return returnedUserDto;
    }

    public ReturnedUserDto registerUser(RegisterUserDto registerUserDto) throws EmailAlreadyExistException {
        User user = userRepository.findUserByEmail(registerUserDto.getEmail()).orElse(null);
        if(user != null){
            throw new EmailAlreadyExistException("There is an account with that email address already exist: " );
        }
        user =new User();
        user.setRole(Role.EMPLOYEE);
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setEmail(registerUserDto.getEmail());
        user.setName(registerUserDto.getName());
        userRepository.save(user);
        ReturnedUserDto returnedUserDto = new ReturnedUserDto();
        returnedUserDto.setName(user.getName());
        returnedUserDto.setUserId(user.getId());
        return returnedUserDto;
    }
}
