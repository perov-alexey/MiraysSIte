package com.mirays.controller;

import com.mirays.entities.Commission;
import com.mirays.repositories.CommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/commission")
public class CommissionController {

    @Autowired
    private CommissionRepository commissionRepository;

    @GetMapping(path = "/add")
    public @ResponseBody String addCommission(@RequestParam String owner) {
        Commission commission = new Commission();
        commission.setOwner(owner);
        commissionRepository.save(commission);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Commission> getAllCommissions() {
        return commissionRepository.findAll();
    }
}
