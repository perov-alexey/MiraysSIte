package com.mirays.controller;

import com.mirays.entities.Commission;
import com.mirays.entities.StageName;
import com.mirays.services.CommissionService;
import com.mirays.services.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Controller for working with commissions.
 */
@RestController
@RequestMapping("/commission")
public class CommissionController {

    @Autowired
    private CommissionService commissionService;
    @Autowired
    private StageService stageService;

    /**
     * Add new or modified existing commission. If there is no stage, this is new user, else existed.
     * @param commission Commission which you want to add or modify.
     */
    @PostMapping("/add")
    public void addCommission(@RequestBody Commission commission) {
        //TODO It seems this controller break the single responsibility principle, I have to split this container on two controllers
        if (commission.getCurrentStage() == null) commission = commissionService.createNewCommission(commission.getOwner());
        commissionService.save(commission);
    }

    @PostMapping(value = "/updateStage")
    public ResponseEntity changeStage(@RequestParam Long commissionId,
                                      @RequestParam StageName stageName,
                                      @RequestParam MultipartFile image) throws IOException {
        //TODO This controller potentially can take Commission as RequestParam or RequestBody. This is more correct approach.
        Commission commission = commissionService.findOne(commissionId);
        commissionService.update(commission, stageService.save(stageName, image.getBytes()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image/{commissionId:.+}/{stageName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getStageImage(@PathVariable Long commissionId,
                                              @PathVariable StageName stageName) {
        Resource file = commissionService.loadImageAsResource(commissionId, stageName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Commission> getAllCommissions() {
        return commissionService.findAll();
    }
}
