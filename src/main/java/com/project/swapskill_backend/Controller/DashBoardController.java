package com.project.swapskill_backend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DashBoardController {

    @GetMapping("/dashboard")
    public Map<String, String> dashboard() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Entered dashboard after successful login");
        return response;
    }
}
