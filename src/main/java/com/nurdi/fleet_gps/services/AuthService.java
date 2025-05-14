package com.nurdi.fleet_gps.services;

import com.nurdi.fleet_gps.securities.JwtService;
import com.nurdi.fleet_gps.utils.dtos.AuthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;

    public AuthDto.ResponseLoginDto login(AuthDto.RequestLoginDto requestLoginDto) {

        String hardcodedUsername = "nurdiansyah";
        String hardcodedPassword = "nurdiansyah";

        if (!requestLoginDto.getUsername().equals(hardcodedUsername) ||
                !requestLoginDto.getPassword().equals(hardcodedPassword)) {
            throw new IllegalStateException("Invalid credentials");
        }

        String token = jwtService.generateToken(hardcodedUsername);

        return new AuthDto.ResponseLoginDto(token);
    }
}
