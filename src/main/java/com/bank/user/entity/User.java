package com.bank.user.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobNumber;
}
