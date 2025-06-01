package com.bank.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDto {
    private String firstName;
    private String lastName;
    private String email;
    private String mobNumber;
    private String password;
}
