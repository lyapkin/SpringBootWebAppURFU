package ru.liapkin.springbootwebappurfu.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.liapkin.springbootwebappurfu.dto.UserAuthDto;
import ru.liapkin.springbootwebappurfu.dto.UserDto;
import ru.liapkin.springbootwebappurfu.entity.Role;
import ru.liapkin.springbootwebappurfu.entity.User;
import ru.liapkin.springbootwebappurfu.enums.RoleTypes;
import ru.liapkin.springbootwebappurfu.repository.RoleRepository;
import ru.liapkin.springbootwebappurfu.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {

        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName(RoleTypes.READER.getType());
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);

    }

    @Override
    public void saveChangeAuthUser(UserAuthDto userAuthDto) {
        Optional<User> userOpt = userRepository.findById(userAuthDto.getId());

        if (userOpt.isPresent()) {

            User user = userOpt.get();

            List<String> rolesToSet = userAuthDto.getRoles().stream()
                    .filter(r -> !user.getRoles().contains(new SimpleGrantedAuthority(r))).toList();
            List<Role> roles = roleRepository.findByNameIn(rolesToSet);
            user.getRoles().removeIf(r -> !rolesToSet.contains(r.getName()));
            user.getRoles().addAll(roles);
            userRepository.save(user);

        } else {
            throw new RuntimeException("Пользователь с таким идентификатором не существует");
        }

    }

    @Override
    public UserDto findUserByEmail(String email) {
        return mapToUserDto(userRepository.findByEmail(email));
    }

    @Override
    public UserAuthDto getUserAuthDtoById(long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            return mapToUserAuthDto(userOpt.get());
        }
        throw new RuntimeException("Нет пользователя с таким идентификатором");
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(long userId) {
        userRepository.deleteById(userId);
    }



    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        if (str.length > 1) {
            userDto.setLastName(str[1]);
        }
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        return userDto;
    }

    private UserAuthDto mapToUserAuthDto(User user) {
        UserAuthDto userAuthDto = new UserAuthDto();
        userAuthDto.setId(user.getId());
        userAuthDto.setEmail(user.getEmail());
        userAuthDto.setRoles(user.getRoles().stream().map(Role::getName).toList());
        return userAuthDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName(RoleTypes.USER.getType());
        return roleRepository.save(role);
    }

    @Override
    public boolean emailExists(String email) {
        User existingUser = userRepository.findByEmail(email);
        return existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty();
    }
}
