package org.oril.service;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.oril.exceptions.NotValidException;
import org.oril.models.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.oril.dto.UserDTO;
import org.oril.models.User;
import org.oril.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    public ModelMapper modelMapper;

    @Transactional(transactionManager = "transactionManager")
    public User save(UserDTO userDTO) {
        if(userRepository.findByName(userDTO.getName()) != null)
            throw new NotValidException("Это имя пользователя уже занято. Попробуйте другое");
        User user = convertToUser(userDTO);
        System.out.println(user);
        return userRepository.save(user);
    }

    @Transactional(transactionManager = "transactionManager")
    public User login(UserDTO userDTO) {
        User user = userRepository.findByName(userDTO.getName());
        if (BCrypt.checkpw(userDTO.getPassword(), user.getPassword()))
            return user;
        throw new NotValidException("Неверный логин или пароль!");
    }

//    private User convertToUser(UserDTO user) {
//        return modelMapper.map(user, User.class);
//    }

    private User convertToUser(UserDTO user) {
        User usr = new User();
        usr.setName(user.getName());
        usr.setPassword(user.getPassword());
        usr.setRole(Roles.USER);
        return usr;
    }

}
