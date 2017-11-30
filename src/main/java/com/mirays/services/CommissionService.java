package com.mirays.services;

import com.mirays.entities.Commission;
import com.mirays.entities.Stage;
import com.mirays.entities.StageName;
import com.mirays.repositories.CommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CommissionService {

    @Autowired
    private CommissionRepository commissionRepository;
    @Autowired
    private StageService stageService;

    public Commission save(Commission commission) {
        return commissionRepository.save(commission);
    }

    public Iterable<Commission> findAll() {
        return commissionRepository.findAllByOrderById();
    }

    public Commission findOne(Long id) {
        return commissionRepository.findOne(id);
    }

    /**
     * This method saves updated commission with new stage. This method updates current stage with new stage and add it
     * to the stage list of commission
     * @param commission Updating commission
     * @param newStage Stage which should be added to commission
     * @return Updated commission
     */
    public Commission update(Commission commission, Stage newStage) {
        commission.setCurrentStage(newStage);
        commission.getStages().removeIf(stage -> stage.getStageName().equals(newStage.getStageName()));
        commission.getStages().add(newStage);
        return commissionRepository.save(commission);
    }

    /**
     * Create new commission
     * @param owner Owner name
     * @return New commission with set owner and initial stage
     */
    public Commission createNewCommission(String owner) {
        Stage initialStage = stageService.getInitialStage();

        Commission commission = new Commission();
        commission.setOwner(owner);
        commission.setCurrentStage(initialStage);
        commission.setStages(Arrays.asList(initialStage));

        return commission;
    }

    /**
     * Loads image by commission ID and stage name as spring resource
     * @param commissionId ID of commission
     * @param stageName Name of the stage which we want to load image
     * @return Stage image as resource
     */
    public Resource loadImageAsResource(Long commissionId, StageName stageName) throws FileNotFoundException {
        Commission commission = findOne(commissionId);
        Stage stage = getStageByName(commission, stageName).orElseThrow(() -> new FileNotFoundException("Image not found"));
        return new ByteArrayResource(stage.getImage());
    }

    public Optional<Stage> getStageByName(Commission commission, StageName stageName) {
        return commission.getStages().stream().filter(stage -> stageName.equals(stage.getStageName())).findFirst();
    }

}
