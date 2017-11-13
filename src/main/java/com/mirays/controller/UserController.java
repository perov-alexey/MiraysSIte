package com.mirays.controller;

import com.mirays.services.CustomApplicationUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class UserController {

    @RequestMapping(value = "/user")
    public Map<String, String> getCurrentUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof AnonymousAuthenticationToken) {
            return Collections.emptyMap();
        } else {
            return Collections.singletonMap("userName", SecurityContextHolder.getContext().getAuthentication().getName());
        }

    }

}
