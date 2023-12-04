package com.javahunter.emailnotification.service;

import com.javahunter.emailnotification.constants.UserConstants;
import com.javahunter.emailnotification.entity.User;
import com.javahunter.emailnotification.exception.ResourceNotFoundException;
import com.javahunter.emailnotification.exception.UserAlreadyExistsException;
import com.javahunter.emailnotification.mapper.UserMapper;
import com.javahunter.emailnotification.payload.EmailDetails;
import com.javahunter.emailnotification.payload.RequestDto;
import com.javahunter.emailnotification.payload.UserDetailsDto;
import com.javahunter.emailnotification.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.email.name}")
    private String emailExchange;

    @Value("${rabbitmq.binding.email.name}")
    private String emailRoutingKey;
    @Override
    public void registerUser(RequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail()))
            throw new UserAlreadyExistsException(UserConstants.USER_ALREADY_EXISTS);
        User user = UserMapper.mapToUser(new User(),requestDto);
        ;
        userRepository.save(user);
        rabbitTemplate.convertAndSend(emailExchange,
                emailRoutingKey,
                EmailDetails.builder()
                .messageBody("Registration Successful with mail id: "+requestDto.getEmail())
                .recipient(requestDto.getEmail())
                .subject("REGISTRATION SUCCESS")
                .build());
    }

    @Override
    public UserDetailsDto getUserByEmail(String email) {
        if(!userRepository.existsByEmail(email))
            throw new ResourceNotFoundException(UserConstants.USER_NOT_FOUND);
        User user = userRepository.findByEmail(email);
        return UserMapper.mapToUserDetails(new UserDetailsDto(),user);
    }

    @Override
    public List<UserDetailsDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty())
            throw new ResourceNotFoundException(UserConstants.USER_NOT_FOUND);
        List<UserDetailsDto> userDetailsDtos = new ArrayList<>();
        users.forEach(user -> userDetailsDtos.add(UserMapper.mapToUserDetails(new UserDetailsDto(),user)));
        return userDetailsDtos;
    }

    @Transactional
    @Override
    public boolean updateUser(RequestDto requestDto) {
        if(!userRepository.existsByEmail(requestDto.getEmail()))
            throw new ResourceNotFoundException(UserConstants.USER_NOT_FOUND);
        User user = userRepository.findByEmail(requestDto.getEmail());
        User updatedUser = UserMapper.mapToUser(user,requestDto);
        userRepository.save(updatedUser);
        return true;
    }

    @Transactional
    @Override
    public boolean deleteUser(String email) {
        if(!userRepository.existsByEmail(email))
            throw new ResourceNotFoundException(UserConstants.USER_NOT_FOUND);
        userRepository.deleteByEmail(email);
        return true;
    }
}
