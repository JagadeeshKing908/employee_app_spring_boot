package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    private final EmployeeRepository repo;

    public EmployeeController(EmployeeRepository repo) {
        this.repo = repo;
    }

    // ADMIN HOME
    @GetMapping("/")
    public String home(Model model, HttpSession session) {

        Employee user = (Employee) session.getAttribute("user");

        if (user == null) return "redirect:/login";

        if (!"ADMIN".equals(user.getRole())) {
            return "redirect:/employee/dashboard";
        }

        model.addAttribute("list", repo.findAll());
        return "index";
    }

    // ADD FORM
    @GetMapping("/add")
    public String addForm(Model model, HttpSession session) {

        Employee user = (Employee) session.getAttribute("user");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("employee", new Employee());
        return "form";
    }

    // SAVE
    @PostMapping("/save")
    public String save(@ModelAttribute Employee emp, HttpSession session) {

        Employee user = (Employee) session.getAttribute("user");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }

        repo.save(emp);
        return "redirect:/";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {

        Employee user = (Employee) session.getAttribute("user");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }

        repo.deleteById(id);
        return "redirect:/";
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model, HttpSession session) {

        Employee user = (Employee) session.getAttribute("user");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("employee", repo.findById(id).orElse(null));
        return "form";
    }

    // EMPLOYEE DASHBOARD
    @GetMapping("/employee/dashboard")
    public String employeeDashboard(HttpSession session, Model model) {

        Employee user = (Employee) session.getAttribute("user");

        if (user == null) return "redirect:/login";

        model.addAttribute("emp", user);
        return "employee-dashboard";
    }

    // UPDATE SKILLS (EMPLOYEE ONLY)
    @PostMapping("/employee/update-skills")
    public String updateSkills(@RequestParam String skills, HttpSession session) {

        Employee user = (Employee) session.getAttribute("user");

        user.setSkills(skills);
        repo.save(user);

        session.setAttribute("user", user);

        return "redirect:/employee/dashboard";
    }
}
