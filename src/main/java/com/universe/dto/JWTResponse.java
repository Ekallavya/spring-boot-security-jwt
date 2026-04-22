package com.universe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTResponse {

    private String user;
    private String access_token;
    private Date  issuedAt;
}
