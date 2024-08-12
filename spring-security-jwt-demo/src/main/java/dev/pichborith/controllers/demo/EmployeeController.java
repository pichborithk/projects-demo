package dev.pichborith.controllers.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @GetMapping
    public String get() {
        return "GET:: employee controller";
    }
    @PostMapping
    public String post() {
        return "POST:: employee controller";
    }
    @PutMapping
    public String put() {
        return "PUT:: employee controller";
    }
    @DeleteMapping
    public String delete() {
        return "DELETE:: employee controller";
    }
}
