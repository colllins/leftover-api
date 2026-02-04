package com.collins.leftover.service;

import com.collins.leftover.dto.user.AuthResponseDto;
import com.collins.leftover.dto.user.RegisterRequestDto;
import com.collins.leftover.model.User;
import com.collins.leftover.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponseDto registerUser(RegisterRequestDto requestDto){

        //checking if email is already in use
        String email = requestDto.getEmail().trim().toLowerCase();
        if(!userRepository.existsByEmail(email)){

            //creating new user using requestDto which has validation annotation and its sets udpatedAt and createdAt and saves user in the db
             User user = new User(requestDto.getName(), email,requestDto.getPayFrequency(), requestDto.getOnBoardingDate());
            userRepository.save(user);

            //using the newly created user to create a reponseDto for client viewing
            return  new AuthResponseDto(user.getId(), user.getName());
        }else{
            //throwing a specific exception for this situation
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exist! Try Again!");
        }
    }

    public AuthResponseDto getUserById(Long userId){
        //repository method findById() returns optional, the exception been thrown is the for case where no user was found for that id
            User user = userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id Not Found!"));

            ////if user was found, use it to create and return the user responseDto
            return new AuthResponseDto(user.getId(), user.getName());
    }

    public AuthResponseDto getUserByEmail(String email){
        //repository method findByEmail() returns optional, the exception been thrown is the for case where no user was found for that email
        User user = userRepository.findByEmail(email.trim().toLowerCase()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that email Not Found!"));

        ////if user was found, use it to create and return the user responseDto
        return new AuthResponseDto(user.getId(), user.getName());
    }

    public List<AuthResponseDto> getAllUsers(){
        List<AuthResponseDto> users = new ArrayList<>();

        //get all users from db and with the possibility of the db table been empty we make sure to handle that situation properly
        //for each user we get from the db, we use it to create an appropriate responseDto, add to the list then return the list
        userRepository.findAll().forEach(user -> {
            AuthResponseDto responseDto = new AuthResponseDto(user.getId(), user.getName());
            users.add(responseDto);
            });

            return users;
    }
}
