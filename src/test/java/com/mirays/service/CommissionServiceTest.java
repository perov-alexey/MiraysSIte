package com.mirays.service;

import com.mirays.entities.Commission;
import com.mirays.entities.Stage;
import com.mirays.entities.StageName;
import com.mirays.repositories.CommissionRepository;
import com.mirays.services.CommissionService;
import com.mirays.services.StageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test suit for Commission Service
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommissionServiceTest {

    @Autowired
    private CommissionService commissionService;

    @MockBean
    private CommissionRepository commissionRepository;
    @MockBean
    private StageService stageService;

    /**
     * The commissionService.createNewCommission(ownerName) should create new commission with set user and initial stage.
     */
    @Test
    public void testNewCommissionCreation() {
        String owner = UUID.randomUUID().toString();

        when(stageService.getInitialStage()).thenReturn(new Stage());

        Commission commission = commissionService.createNewCommission(owner);
        assertThat(commission.getId()).isNull();
        assertThat(commission.getOwner()).isEqualTo(owner);
        assertThat(commission.getCurrentStage()).isNotNull();
        assertThat(commission.getStages().size()).isEqualTo(1);
    }

    /**
     *  The commission service should return commission by id.
     */
    @Test
    public void testFindingById() {
        when(commissionRepository.findOne(anyLong())).thenAnswer(invocation -> {
            Stage mockStage = new Stage();

            Commission commission = new Commission();
            commission.setId((Long) invocation.getArguments()[0]);
            commission.setOwner("stub");
            commission.setCurrentStage(mockStage);
            commission.setStages(Arrays.asList(mockStage));
            return commission;
        });

        Long commissionId = 1L;

        Commission commission = commissionService.findOne(commissionId);

        assertThat(commission.getId()).isEqualTo(commissionId);
        assertThat(commission.getOwner()).isNotNull();
        assertThat(commission.getCurrentStage()).isNotNull();
        assertThat(commission.getStages()).isNotEmpty();
    }

    /**
     * Commission service should return image by commissionId and stage name
     */
    @Test
    public void testLoadingImage() throws FileNotFoundException {
        StageName stageName = StageName.FINISHED;
        Long commissionId = 1L;

        when(commissionRepository.findOne(anyLong())).thenAnswer(invocation -> {
            Stage mockStage = new Stage();
            mockStage.setImage(UUID.randomUUID().toString().getBytes());
            mockStage.setStageName(stageName);

            Commission commission = new Commission();
            commission.setId((Long) invocation.getArguments()[0]);
            commission.setCurrentStage(mockStage);
            commission.setStages(Arrays.asList(mockStage));
            return commission;
        });

        Resource image = commissionService.loadImageAsResource(commissionId, stageName);
        assertThat(image).isNotNull();
    }

    /**
     * Commission service should throw exception if image not found
     */
    @Test
    public void testLoadingMissedImage() throws FileNotFoundException {
        StageName stageName = StageName.APPROVED;
        StageName requestedStageName = StageName.FINISHED;
        Long commissionId = 1L;

        when(commissionRepository.findOne(anyLong())).thenAnswer(invocation -> {
            Stage mockStage = new Stage();
            mockStage.setStageName(stageName);

            Commission commission = new Commission();
            commission.setId((Long) invocation.getArguments()[0]);
            commission.setCurrentStage(mockStage);
            commission.setStages(Arrays.asList(mockStage));
            return commission;
        });

        assertThatThrownBy(() -> commissionService.loadImageAsResource(commissionId, requestedStageName))
                .isInstanceOf(FileNotFoundException.class)
                .hasMessage("Image not found");
    }

    /**
     * Then commission updates with next stage, this stage should become current, also stages list should be updated
     * with this stage.
     */
    @Test
    public void testUpdatingCommissionWithNewStage() throws Exception {
        Long commissionId = 1L;
        StageName newStageName = StageName.SKETCH;
        byte[] image = UUID.randomUUID().toString().getBytes();

        Stage approvedStage = new Stage(StageName.APPROVED, new byte[0]);
        Commission commission = new Commission();
        commission.setId(commissionId);
        commission.setCurrentStage(approvedStage);
        commission.setStages(new ArrayList<>(Arrays.asList(approvedStage)));

        when(commissionRepository.save(any(Commission.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Commission updatedCommission = commissionService.update(commission, new Stage(newStageName, image));

        assertThat(updatedCommission.getCurrentStage().getStageName()).isEqualTo(newStageName);
        assertThat(updatedCommission.getCurrentStage().getImage()).isEqualTo(image);
        assertThat(updatedCommission.getStages().size()).isEqualTo(2);

        verify(commissionRepository, times(1)).save(updatedCommission);
    }

}
