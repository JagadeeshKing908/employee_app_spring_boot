package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final EmployeeRepository repo;

    public AuthController(EmployeeRepository repo) {
        this.repo = repo;
    }

    // LOGIN PAGE
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // LOGIN PROCESS
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        // FIXED: Optional handling replaced with safe null check
        Employee user = repo.findByEmail(email).orElse(null);

        // validation
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }

        // store user in session
        session.setAttribute("user", user);

        // ROLE BASED REDIRECTION
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/";
        } else {
            return "redirect:/employee/dashboard";
        }
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
