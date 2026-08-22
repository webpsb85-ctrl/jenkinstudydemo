package com.example.studydevops.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    /**
     * GET request to root endpoint
     * @return welcome message
     */
    @GetMapping("/")
    public String home() {
        return "Welcome to Study DevOps Application!";
    }

    /**
     * GET request to /api/hello
     * @return hello message
     */
    @GetMapping("/api/hello")
    public String hello() {
        return "Hello from StudyDevOps!";
    }

    /**
     * GET request with path variable
     * @param name the name to greet
     * @return personalized greeting
     */
    @GetMapping("/api/greet/{name}")
    public String greet(@PathVariable String name) {
        return "Hello, " + name + "! Welcome to StudyDevOps.";
    }

    /**
     * GET request with query parameters
     * @param firstName first name query parameter
     * @param lastName last name query parameter
     * @return personalized message with full name
     */
    @GetMapping("/api/user")
    public String getUser(@RequestParam(required = false, defaultValue = "Guest") String firstName,
                          @RequestParam(required = false, defaultValue = "User") String lastName) {
        return "User: " + firstName + " " + lastName;
    }

    /**
     * GET request to check application status
     * @return status information
     */
    @GetMapping("/api/status")
    public String getStatus() {
        return "Application is running successfully!";
    }
}
