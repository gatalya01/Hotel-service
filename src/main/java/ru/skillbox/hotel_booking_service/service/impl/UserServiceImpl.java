package ru.skillbox.hotel_booking_service.service.impl;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.hotel_booking_service.entity.RoleType;
import ru.skillbox.hotel_booking_service.entity.User;
import ru.skillbox.hotel_booking_service.events.UserRegistrationEvent;
import ru.skillbox.hotel_booking_service.exception.EntityNotFoundException;
import ru.skillbox.hotel_booking_service.mapper.UserMapper;
import ru.skillbox.hotel_booking_service.repository.UserRepository;
import ru.skillbox.hotel_booking_service.service.UserService;
import ru.skillbox.hotel_booking_service.utils.BeanUtils;
import ru.skillbox.hotel_booking_service.web.model.*;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${app.kafka.userRegistrations}")
    private String userRegistrationsTopic;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, UserRegistrationEvent> kafkaTemplate;

    @Override
    public UserListResponse findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userMapper.userListToUserListResponse(userRepository.findAll(pageable));
    }

    @Override
    public UserResponse findById(Long id) {
        return userMapper.userToResponse(userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("User with ID {0} not found", id))));
    }

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found" + username));
    }

    @Override
    public UserResponse save(UpsertUserRequest request, RoleType roleType) {

        if (existsByUsernameAndEmail(request.getUsername(), request.getEmail())) {
            throw new EntityExistsException(MessageFormat.format("Username {0} or email {1} already exists",
                    request.getUsername(), request.getEmail()));
        }

        User user = userMapper.requesttoUser(request);
        user.setRole(roleType);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        UserRegistrationEvent event = new UserRegistrationEvent(savedUser.getId(), LocalDateTime.now());
        log.info("Saved user registration event: {}", event);
        kafkaTemplate.send(userRegistrationsTopic, event);
        return userMapper.userToResponse(savedUser);
    }


    @Override
    public UserResponse update(Long id, UpdateUserRequest request, RoleType roleType) {

        User existedUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("User with ID {0} not found", id)));

        User updatedUser = userMapper.requesttoUser(id, request);

        if (updatedUser.getPassword() != null) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        BeanUtils.copyUserNotNullProperties(updatedUser, existedUser);

        if (roleType != null) {
            existedUser.setRole(roleType);
        }


        return userMapper.userToResponse(userRepository.save(existedUser));
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsernameAndEmail(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username, email);
    }
}
