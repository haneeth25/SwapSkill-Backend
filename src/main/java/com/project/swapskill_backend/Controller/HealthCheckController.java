package com.project.swapskill_backend.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @CrossOrigin(origins = "*")
    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }

}
