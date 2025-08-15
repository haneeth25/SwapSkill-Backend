package com.project.swapskill_backend.Utils;

import com.project.swapskill_backend.Service.SecurityService.JwtService;

public class SwapSkillUtils {
    public static String getUsername(String authHeader){
        String token = authHeader.substring(7);
        return JwtService.extractUsername(token);
    }
}
