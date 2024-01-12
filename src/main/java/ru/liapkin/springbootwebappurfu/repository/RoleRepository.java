package ru.liapkin.springbootwebappurfu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liapkin.springbootwebappurfu.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    List<Role> findByNameIn(List<String> names);
}
