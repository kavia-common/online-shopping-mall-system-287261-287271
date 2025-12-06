package com.example.springbootbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * REST controller for basic application endpoints
 * Provides health check, info, and documentation access
 */
@RestController
@Tag(name = "Application", description = "Basic application endpoints")
public class HelloController {
    
    // PUBLIC_INTERFACE
    /**
     * Welcome endpoint
     * 
     * @return Welcome message
     */
    @GetMapping("/")
    @Operation(summary = "Welcome endpoint", description = "Returns a welcome message")
    public String hello() {
        return "Welcome to Online Shopping Mall API! Visit /docs for API documentation.";
    }
    
    // PUBLIC_INTERFACE
    /**
     * Redirects to Swagger UI documentation
     * 
     * @param request HTTP request
     * @return Redirect to Swagger UI
     */
    @GetMapping("/docs")
    @Operation(summary = "API Documentation", description = "Redirects to Swagger UI preserving original scheme/host/port")
    public RedirectView docs(HttpServletRequest request) {
        String target = UriComponentsBuilder
                .fromHttpUrl(request.getRequestURL().toString())
                .replacePath("/swagger-ui.html")
                .replaceQuery(null)
                .build()
                .toUriString();

        RedirectView rv = new RedirectView(target);
        rv.setHttp10Compatible(false);
        return rv;
    }
    
    // PUBLIC_INTERFACE
    /**
     * Health check endpoint
     * 
     * @return Health status
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Returns application health status")
    public String health() {
        return "OK";
    }
    
    // PUBLIC_INTERFACE
    /**
     * Application info endpoint
     * 
     * @return Application information
     */
    @GetMapping("/api/info")
    @Operation(summary = "Application info", description = "Returns application information")
    public String info() {
        return "Online Shopping Mall System - Spring Boot Backend API v1.0.0";
    }
}
