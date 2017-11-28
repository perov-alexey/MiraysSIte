package com.mirays.service;

import com.mirays.entities.Commission;
import com.mirays.entities.StageName;
import com.mirays.services.CommissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suit for Commission Service
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommissionServiceTest {

    @Autowired
    private CommissionService commissionService;

    /**
     * The commissionService.createNewCommission(ownerName) should create new commission with setted user and initial stage.
     */
    @Test
    public void testNewCommissionCreation() {
        String owner = UUID.randomUUID().toString();

        Commission commission = commissionService.createNewCommission(owner);
        assertThat(commission.getId()).isNull();
        assertThat(commission.getOwner()).isEqualTo(owner);
        assertThat(commission.getCurrentStage()).isNotNull();
        assertThat(commission.getCurrentStage().getStageName()).isEqualTo(StageName.APPROVED);
        assertThat(commission.getStages().size()).isEqualTo(1);
    }

}
