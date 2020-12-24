package com.bdg.springrest.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
}
