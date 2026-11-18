package com.hqms.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/api/patient/ping")
    public String patient() {
        return "patient ok";
    }

    @GetMapping("/api/doctor/ping")
    public String doctor() {
        return "doctor ok";
    }

    @GetMapping("/api/admin/ping")
    public String admin() {
        return "admin ok";
    }
}
