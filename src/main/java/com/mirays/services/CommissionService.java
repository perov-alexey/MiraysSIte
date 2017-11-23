package com.mirays.services;

import com.mirays.entities.Commission;
import com.mirays.entities.Stage;
import com.mirays.entities.StageName;
import com.mirays.repositories.CommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.ListIterator;
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

    public Commission update(Commission commission, Stage newStage) {
        commission.setCurrentStage(newStage);
        ListIterator<Stage> stagesIterator = commission.getStages().listIterator();
        while (stagesIterator.hasNext()) {
            if (newStage.getStageName().equals(stagesIterator.next().getStageName())) {
                stagesIterator.set(newStage);
            }
        }
        return commissionRepository.save(commission);
    }

    public Commission createNewCommission(String owner) {
        Stage initialStage = stageService.getInitialStage();

        Commission commission = new Commission();
        commission.setOwner(owner);
        commission.setCurrentStage(initialStage);
        commission.setStages(Arrays.asList(initialStage));

        return new Commission();
    }

    public Resource loadImageAsResource(Long commissionId, StageName stageName) {
        Commission commission = findOne(commissionId);
        Stage stage = getStageByName(commission, stageName).get();
        return new ByteArrayResource(stage.getImage());
    }

    public Optional<Stage> getStageByName(Commission commission, StageName stageName) {
        return commission.getStages().stream().filter(stage -> stageName.equals(stage.getStageName())).findFirst();
    }

}
