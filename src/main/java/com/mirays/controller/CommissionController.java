package com.mirays.controller;

import com.mirays.entities.Commission;
import com.mirays.entities.StageName;
import com.mirays.services.CommissionService;
import com.mirays.services.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
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
     * Add new commission.
     * @param ownerName Name of the commission owner.
     * @return OK status
     */
    @PostMapping("/add")
    public ResponseEntity addCommission(@RequestBody String ownerName) {
        commissionService.save(commissionService.createNewCommission(ownerName));
        //TODO Probably it would be good if I return different codes, for example if commission isn't saved.
        return ResponseEntity.ok().build();
    }

    /**
     * Update owner name
     * @param commission Commission where owner name should be changed
     * @return OK status
     */
    @PostMapping("/updateOwner")
    public ResponseEntity updateCommissionOwner(@RequestBody Commission commission) {
        commissionService.save(commission);
        return ResponseEntity.ok().build();
    }

    /**
     * Change stage of commission with uploaded image
     * @param commissionId Updated commission
     * @param stageName Stage name for creating stage
     * @param image Image for creating stage
     * @return  OK status
     * @throws IOException In case of access issues
     */
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
                                              @PathVariable StageName stageName) throws FileNotFoundException {
        //TODO I have to appropriate handle FileNotFoundException
        Resource file = commissionService.loadImageAsResource(commissionId, stageName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Commission> getAllCommissions() {
        return commissionService.findAll();
    }
}
