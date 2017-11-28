package com.mirays.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirays.entities.Commission;
import com.mirays.entities.Stage;
import com.mirays.services.CommissionService;
import com.mirays.services.StageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test suit for Commission Controller
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CommissionController.class)
public class CommissionControllerTests {

    @Autowired
    private CommissionController controller;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private CommissionService commissionService;
    @MockBean
    private StageService stageService;

    /**
     * Commission Controller should exist
     */
    @Test
    public void testControllerExistence() {
        assertThat(controller).isNotNull();
    }

    /**
     * /commission/all method should return all commissions
     */
    @Test
    public void testRequestAllCommission() throws Exception {
        when(commissionService.findAll()).thenReturn(Arrays.asList(
                new Commission(), new Commission()
        ));

        this.mockMvc.perform(get("/commission/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    /**
     * Then new commission is adding, should be called the commissionService.createNewCommission
     */
    @Test
    public void testNewCommissionAdding() throws Exception {
        String owner = UUID.randomUUID().toString();

        this.mockMvc.perform(post("/commission/add")
                .content(owner)
                .with(user("user"))
        )
                .andExpect(status().isOk());

        verify(commissionService, times(1)).createNewCommission(owner);
    }

    /**
     * Then existing commission is modifying, shouldn't be called the commissionService.createNewCommission
     */
    @Test
    public void testCommissionModification() throws Exception {
        Commission commission = new Commission();
        commission.setCurrentStage(new Stage());

        this.mockMvc.perform(post("/commission/updateOwner")
                .content(this.mapper.writeValueAsString(commission))
                .contentType(MediaType.APPLICATION_JSON)
                .with(user("user"))
        )
                .andExpect(status().isOk());

        verify(commissionService, times(0)).createNewCommission(any());
        verify(commissionService, times(1)).save(commission);
    }

}
