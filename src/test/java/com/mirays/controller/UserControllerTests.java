package com.mirays.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test suit for User Controller
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private UserController controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    /**
     * User Controller should exist
     */
    @Test
    public void testControllerExistence() {
        assertThat(controller).isNotNull();
    }

    /**
     * If the user not authorized, User Controller should return empty string
     */
    @Test
    public void testGettingUserNameForUnauthorizedUser() throws Exception {
        this.mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").doesNotExist());
    }

    /**
     * If the user authorized, User Controller should return user name
     */
    @Test
    public void testGettingUserNameForAuthorizedUser() throws Exception {
        String username = UUID.randomUUID().toString();

        this.mockMvc.perform(get("/user").with(user(username)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(username));
    }

}
