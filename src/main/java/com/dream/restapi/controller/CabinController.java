package com.dream.restapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dream.restapi.model.Cabin;
import com.dream.restapi.services.CabinService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/cabin")
public class CabinController {

    @Autowired
    private CabinService cabinService;

    @GetMapping()
    public List<Cabin> getCabins() {
        return this.cabinService.getAllCabins();
    }
    
}
