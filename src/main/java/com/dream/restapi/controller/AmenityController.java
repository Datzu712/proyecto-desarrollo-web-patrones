package com.dream.restapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dream.restapi.model.Amenity;
import com.dream.restapi.services.AmenityService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/amenity")
public class AmenityController {

    @Autowired
    private AmenityService amenityService;

    @GetMapping()
    public List<Amenity> getMethodName() {
        return this.amenityService.getAllAmenities();
    }

}
