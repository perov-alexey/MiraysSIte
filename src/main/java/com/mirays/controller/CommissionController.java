package com.mirays.controller;

import com.mirays.entities.Commission;
import com.mirays.entities.Stage;
import com.mirays.repositories.CommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/commission")
public class CommissionController {

    @Autowired
    private CommissionRepository commissionRepository;

    @PostMapping(path = "/add")
    public void addCommission(@RequestBody Commission commission) {
        if (commission.getStage() == null) commission.setStage(Stage.APPROVED);
        commissionRepository.save(commission);
    }

    @GetMapping(path = "/all")
    public Iterable<Commission> getAllCommissions() {
        return commissionRepository.findAllByOrderById();
    }
}
