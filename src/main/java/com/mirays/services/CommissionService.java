package com.mirays.services;

import com.mirays.entities.Commission;
import com.mirays.entities.Stage;
import com.mirays.repositories.CommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommissionService {

    @Autowired
    private CommissionRepository commissionRepository;

    public Commission save(Commission commission) {
        return commissionRepository.save(commission);
    }

    public Iterable<Commission> findAll() {
        return commissionRepository.findAllByOrderById();
    }

    public Commission updateStage(Commission commission, Stage stage) {
        commission.setStage(stage);
        return commissionRepository.save(commission);
    }

}
