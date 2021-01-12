package com.bdg.bank.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;

    @Size(min = 5, max = 30, message = "Username length should be in range [8,30]")
    private String username;

    @Size(min = 7, message = "password length should be greater than 7")
    private String password;

    @Size(min = 3, max = 50, message = "First name length should be in range [3,50]")
    private String firstName;

    @Size(min = 3, max = 100, message = "First name length should be in range [3,100]")
    private String lastName;
}
