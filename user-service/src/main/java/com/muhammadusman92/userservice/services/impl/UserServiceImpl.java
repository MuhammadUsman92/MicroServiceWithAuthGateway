package com.muhammadusman92.userservice.services.impl;

import com.muhammadusman92.userservice.config.AppConstants;
import com.muhammadusman92.userservice.exception.AlreadyExistExeption;
import com.muhammadusman92.userservice.exception.ResourceNotFoundException;
import com.muhammadusman92.userservice.model.Role;
import com.muhammadusman92.userservice.model.User;
import com.muhammadusman92.userservice.payloads.UserDto;
import com.muhammadusman92.userservice.repo.RoleRepo;
import com.muhammadusman92.userservice.repo.UserRepo;
import com.muhammadusman92.userservice.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        if(userRepo.existsUserByEmail(user.getEmail())){
            throw new AlreadyExistExeption("email",user.getEmail());
        }
        Role role = roleRepo.findById(AppConstants.NORMAL_USER)
                        .orElseThrow(()->new ResourceNotFoundException("Role","Id",AppConstants.NORMAL_USER));
        user.getRoles().add(role);
        user.setPassword(user.getPassword());
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        User findUser = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        User user = modelMapper.map(userDto, User.class);
        user.setId(findUser.getId());
        user.setRoles(findUser.getRoles());
        user.setEmail(findUser.getEmail());
        user.setPassword(user.getPassword());
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User findUser = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        return modelMapper.map(findUser,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long userId) {
        User findUser = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        userRepo.delete(findUser);
    }

    @Override
    public User findByEmail(String email) {
        User findUser = userRepo.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",email));
        return findUser;
    }
}
