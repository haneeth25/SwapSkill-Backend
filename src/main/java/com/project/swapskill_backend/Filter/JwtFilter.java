package com.project.swapskill_backend.Filter;

import com.project.swapskill_backend.Service.SecurityService.CustomUserDetailService;
import com.project.swapskill_backend.Service.SecurityService.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    CustomUserDetailService customUserDetailService;

    private boolean isExcludedPath(String path) {
        return path.startsWith("/auth/") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = "";
        String username = "";
        String path = request.getServletPath();
        if (isExcludedPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        if((authHeader != null) && (authHeader.startsWith("Bearer "))){
            token = authHeader.substring(7);
            username = JwtService.extractUsername(token);
        }

        if (username != null) {
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
            if (JwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        System.out.println(
                "[DEBUG] SecurityContext Authentication: " +
                        SecurityContextHolder.getContext().getAuthentication()
        );
        filterChain.doFilter(request,response);
    }
}
