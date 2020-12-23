package com.jvxie.report.controllers;

import com.jvxie.report.services.impls.AreaMapServiceImpl;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("BaseAreaMapController")
@RequestMapping("/area_map")
public class AreaMapController {
    @Autowired
    AreaMapServiceImpl areaMapService;

    @GetMapping("/")
    ResponseVo list() {
        return areaMapService.getAll();
    }
}
