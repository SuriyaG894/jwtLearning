package com.suriya.JWTAuthentication.controller;

import com.suriya.JWTAuthentication.DTO.UserDTO;
import com.suriya.JWTAuthentication.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @GetMapping("/authenticated")
    public String generateToken(@RequestBody UserDTO userDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword()));
        return jwtUtils.generateToken();
    }

}
