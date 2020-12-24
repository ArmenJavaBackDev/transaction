package com.bdg.springrest.services;

import com.bdg.springrest.form.UserForm;
import com.bdg.springrest.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<User> findAll();
    Optional<User> findById(Long id);
    void signUp(UserForm userForm);
}
