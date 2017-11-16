package com.mirays.controller;

import com.mirays.entities.Commission;
import com.mirays.entities.StageName;
import com.mirays.services.CommissionService;
import com.mirays.services.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/commission")
public class CommissionController {

    @Autowired
    private CommissionService commissionService;
    @Autowired
    private StageService stageService;

    @PostMapping("/add")
    public void addCommission(@RequestBody Commission commission) {
        if (commission.getStage() == null) commission.setStage(stageService.getInitialStage());
        commissionService.save(commission);
    }

    @PostMapping("/updateStage")
    public ResponseEntity changeStage(@RequestParam Commission commission,
                                      @RequestParam MultipartFile image,
                                      @RequestParam StageName stageName) throws IOException {
        commissionService.updateStage(commission, stageService.save(stageName, image.getBytes()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public Iterable<Commission> getAllCommissions() {
        return commissionService.findAll();
    }
}
