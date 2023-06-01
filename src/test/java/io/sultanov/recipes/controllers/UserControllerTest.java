package io.sultanov.recipes.controllers;

//import io.sultanov.recipes.api.controllers.LoginController;
//import io.sultanov.recipes.api.controllers.UserController;
//import io.sultanov.recipes.api.services.AuthService;
//import io.sultanov.recipes.api.services.UserService;
//import io.sultanov.recipes.security.filtering.JwtFilter;
//import io.sultanov.recipes.security.filtering.SecurityConfig;
//import io.sultanov.recipes.security.models.JwtAuthentication;
//import io.sultanov.recipes.security.utils.JwtProvider;
//import io.sultanov.recipes.security.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@WebMvcTest(controllers = {LoginController.class, UserController.class})
//@Import(value = {JwtFilter.class, SecurityConfig.class, JwtAuthentication.class,
//        JwtProvider.class, JwtUtils.class, AuthService.class, UserService.class})
public class UserControllerTest {

    @Test
    public void test() {
        int a = 1;
        assertEquals(1, a);
    }
}
