// CurrentUserUtil.java - New file
package com.ecommerce.userservice.util;

import com.ecommerce.userservice.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class CurrentUserUtil {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Get current user ID from JWT token
     * @return User ID from JWT token, or null if not authenticated
     */
    public Long getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return null;
            }

            // Get token from request
            ServletRequestAttributes attributes = (ServletRequestAttributes)
                    RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                return jwtUtil.extractUserId(token);
            }
        } catch (Exception e) {
            // Log error if needed
            return null;
        }
        return null;
    }

    /**
     * Get current username from SecurityContext
     * @return Username, or null if not authenticated
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }
}