package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.jpaRepository.UsersRepository;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import com.epam.esm.mapper.impl.UserMapper;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String ID = "id";
    private static final String ROLE_USER = "ROLE_USER";
    private final UserDao userDao;
    private final UserMapper mapper;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserMapper mapper, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.mapper = mapper;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> readAll(int page, int size) {
        List<User> users = userDao.read(page, size);
        return users.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());    }

    @Override
    public UserDto readById(Long id) {
        Optional<User> userOptional = userDao.readById(id);
     /*   if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND, ID, id);
        }*/
        return mapper.mapToDto(userOptional.get());
    }

    @Transactional
    @Override
    public UserDto create(UserDto newUser) {
        User user = mapper.mapToEntity(newUser);
        User userInDb = userDao.create(user);
        return mapper.mapToDto(userInDb);
    }

    @Transactional
    public void register(User person) {
        person.setRole(ROLE_USER);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        usersRepository.save(person);
    }
}