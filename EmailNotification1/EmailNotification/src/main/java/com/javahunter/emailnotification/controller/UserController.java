package com.javahunter.emailnotification.controller;

import com.javahunter.emailnotification.constants.UserConstants;
import com.javahunter.emailnotification.payload.RequestDto;
import com.javahunter.emailnotification.payload.ResponseDto;
import com.javahunter.emailnotification.payload.UserDetailsDto;
import com.javahunter.emailnotification.service.UserService;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerUser(@RequestBody RequestDto requestDto){
        userService.registerUser(requestDto);
        return new ResponseEntity<>(ResponseDto.builder()
                .statusCode(HttpStatus.CREATED.toString())
                .statusMsg(UserConstants.REGISTRATION_SUCCESS)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/getbyemail/{email}")
    public ResponseEntity<UserDetailsDto> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<UserDetailsDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateUser(RequestDto requestDto){
        boolean isUpdated = userService.updateUser(requestDto);
        if (!isUpdated)
            return new ResponseEntity<>(ResponseDto.builder()
                    .statusCode(HttpStatus.EXPECTATION_FAILED.toString())
                    .statusMsg(UserConstants.UPDATE_FAILED)
                    .build(),HttpStatus.EXPECTATION_FAILED);
        return new ResponseEntity<>(ResponseDto.builder()
                .statusMsg(UserConstants.UPDATE_SUCCESS)
                .statusCode(HttpStatus.OK.toString())
                .build(),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable String email){
        boolean isDeleted = userService.deleteUser(email);
        if (!isDeleted)
            return new ResponseEntity<>(ResponseDto.builder()
                    .statusCode(HttpStatus.EXPECTATION_FAILED.toString())
                    .statusMsg(UserConstants.DELETION_FAILED)
                    .build(),HttpStatus.EXPECTATION_FAILED);
        return new ResponseEntity<>(ResponseDto.builder()
                .statusMsg(UserConstants.DELETION_SUCCESS)
                .statusCode(HttpStatus.OK.toString())
                .build(),HttpStatus.OK);
    }
}
