package ru.liapkin.springbootwebappurfu.bootstrap;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.liapkin.springbootwebappurfu.entity.Role;
import ru.liapkin.springbootwebappurfu.enums.RoleTypes;
import ru.liapkin.springbootwebappurfu.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
@NoArgsConstructor
public class RolesSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private RoleRepository roleRepository;

    @Autowired
    public RolesSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createRoles();
    }

    public void createRoles() {
        List<String> roles = roleRepository.findAll().stream().map(Role::getName).toList();

        List<Role> rolesToSave = new ArrayList<>();
        for (RoleTypes roleType : RoleTypes.values()) {
            if (!roles.contains(roleType.getType())) {
                Role role = new Role();
                role.setName(roleType.getType());
                rolesToSave.add(role);
            }
        }

        if (!rolesToSave.isEmpty()) {
            roleRepository.saveAll(rolesToSave);
        }
    }

}
