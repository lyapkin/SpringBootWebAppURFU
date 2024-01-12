package ru.liapkin.springbootwebappurfu.bootstrap;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.liapkin.springbootwebappurfu.entity.Role;
import ru.liapkin.springbootwebappurfu.entity.User;
import ru.liapkin.springbootwebappurfu.enums.RoleTypes;
import ru.liapkin.springbootwebappurfu.repository.RoleRepository;
import ru.liapkin.springbootwebappurfu.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Component
@Order(2)
@Transactional
@NoArgsConstructor
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminSeeder(RoleRepository roleRepository,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createAdmin();
    }

    public void createAdmin() {
        List<Role> roles = roleRepository.findAll();
        Optional<Role> adminRole = roles.stream()
                .filter(role -> role.getName().equals(RoleTypes.ADMIN.getType())).findFirst();

        if (adminRole.isEmpty()) {
            return;
        }

        boolean adminExists = !roleRepository.findByName(RoleTypes.ADMIN.getType()).getUsers().isEmpty();
        if (adminExists) {
            return;
        }

        User user = new User();
        user.setEmail("admin@admin.ru");
        user.setName(adminName);
        user.setPassword(passwordEncoder.encode(adminPassword));
        user.setRoles(roles);
        userRepository.save(user);
    }
}
