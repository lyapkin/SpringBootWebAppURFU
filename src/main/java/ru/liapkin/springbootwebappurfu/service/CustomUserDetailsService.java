package ru.liapkin.springbootwebappurfu.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.liapkin.springbootwebappurfu.entity.User;
import ru.liapkin.springbootwebappurfu.repository.UserRepository;
import ru.liapkin.springbootwebappurfu.security.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(usernameOrEmail);
        if (user != null) {
            return new CustomUserDetails(user);
//            return new org.springframework.security.core.userdetails.User(user.getEmail(),
//                    user.getPassword(),
//                    user.getRoles()
//                            .stream()
//                            .map((role -> new SimpleGrantedAuthority(role.getName())))
//                            .collect(Collectors.toList()));
        } else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }
}
