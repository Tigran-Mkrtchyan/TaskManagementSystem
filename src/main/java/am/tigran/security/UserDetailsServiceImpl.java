package am.tigran.security;

import am.tigran.models.User;
import am.tigran.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user =userRepository.findUserByEmail(email).orElseThrow(
                ()->new UsernameNotFoundException("user does not exist"));

        return UserSecurity.fromUser(user);
    }
}
