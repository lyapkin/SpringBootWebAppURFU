package ru.liapkin.springbootwebappurfu.service;

import ru.liapkin.springbootwebappurfu.dto.UserAuthDto;
import ru.liapkin.springbootwebappurfu.dto.UserDto;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    UserDto findUserByEmail(String email);

    UserAuthDto getUserAuthDtoById(long id);

    void saveChangeAuthUser(UserAuthDto userAuthDto);

    void deleteById(long userId);

    List<UserDto> findAllUsers();

    boolean emailExists(String email);


}
