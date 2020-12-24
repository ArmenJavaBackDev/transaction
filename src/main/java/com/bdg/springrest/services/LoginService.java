package com.bdg.springrest.services;

import com.bdg.springrest.form.LoginForm;
import com.bdg.springrest.transfer.TokenDto;

public interface LoginService {
    TokenDto login(LoginForm loginForm);
}
