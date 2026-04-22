package com.universe.controller;

import com.universe.dto.AuthRequest;
import com.universe.dto.JWTResponse;
import com.universe.dto.Product;
import com.universe.entity.UserInfo;
import com.universe.service.JwtService;
import com.universe.service.LoginUserService;
import com.universe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/jwt")
public class JwtController {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

     @PostMapping("/addLoginUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return loginUserService.addUser(userInfo);
    }

     @PostMapping("/authenticateAndGetToken")
    public JWTResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = null;
         JWTResponse jwtResponse = null;
        System.out.println("authRequest = " + authRequest);
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }catch (Exception e) {
            System.out.println("Exception = " + e.getMessage());
        }
        System.out.println("authentication = " + authentication);
        if (authentication!=null && authentication.isAuthenticated()) {

            jwtResponse = new JWTResponse();
            jwtResponse.setUser(authRequest.getUsername());
            jwtResponse.setAccess_token(jwtService.generateToken(authRequest.getUsername()));
            jwtResponse.setIssuedAt(new Date());
            return jwtResponse;
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }


    }
}
