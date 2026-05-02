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

    // ================= ADMIN DASHBOARD =================
    @GetMapping({"/", "/admin"})
    public String home(Model model, HttpSession session) {

        Employee user = (Employee) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/employee/dashboard";
        }

        model.addAttribute("list", repo.findAll());
        return "index";
    }

    // ================= ADD FORM =================
    @GetMapping("/add")
    public String addForm(Model model, HttpSession session) {

        Employee user = (Employee) session.getAttribute("user");

        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("employee", new Employee());
        return "form";
    }

    // ================= SAVE (CREATE + UPDATE) =================
    @PostMapping("/save")
    public String save(@ModelAttribute Employee emp, HttpSession session) {

        Employee user = (Employee) session.getAttribute("user");

        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        System.out.println("Incoming ID: " + emp.getId());

        // ================= UPDATE =================
        if (emp.getId() != null) {

            Employee existing = repo.findById(emp.getId()).orElse(null);

            if (existing != null) {

                existing.setName(emp.getName());
                existing.setEmail(emp.getEmail());
                existing.setSkills(emp.getSkills());
                existing.setExperience(emp.getExperience());
                existing.setDepartment(emp.getDepartment());
                existing.setManager(emp.getManager());
                existing.setSalary(emp.getSalary());
                existing.setRole(emp.getRole());

                // 🔥 PASSWORD SAFE UPDATE (IMPORTANT FIX)
                if (emp.getPassword() != null && !emp.getPassword().isEmpty()) {
                    existing.setPassword(emp.getPassword());
                }

                repo.save(existing);
            } else {
                repo.save(emp);
            }

        } else {
            // ================= CREATE =================
            repo.save(emp);
        }

        return "redirect:/admin";
    }

    // ================= DELETE =================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {

        Employee user = (Employee) session.getAttribute("user");

        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        repo.deleteById(id);
        return "redirect:/admin";
    }

    // ================= EDIT =================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model, HttpSession session) {

        Employee user = (Employee) session.getAttribute("user");

        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        Employee emp = repo.findById(id).orElse(null);

        if (emp == null) {
            return "redirect:/admin";
        }

        model.addAttribute("employee", emp);
        return "form";
    }

    // ================= EMPLOYEE DASHBOARD =================
    @GetMapping("/employee/dashboard")
    public String employeeDashboard(HttpSession session, Model model) {

        Employee user = (Employee) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("emp", user);
        return "employee-dashboard";
    }

    // ================= UPDATE SKILLS =================
    @PostMapping("/employee/update-skills")
    public String updateSkills(@RequestParam String skills, HttpSession session) {

        Employee user = (Employee) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        user.setSkills(skills);
        repo.save(user);

        session.setAttribute("user", user);

        return "redirect:/employee/dashboard";
    }
}
