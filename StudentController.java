package com.example.student.controller;

import com.example.student.entity.Student;
import com.example.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String home() {
        return "index";
    }

    @GetMapping("/all")
    public String viewAllStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "create";
    }

    @PostMapping("/create")
    public String saveStudent(
            @Valid @ModelAttribute("student") Student student,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "create";
        }
        studentService.save(student);
        return "redirect:/students/all";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id, @Valid @ModelAttribute("student") Student student,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "edit";
        }
        student.setId(id);
        studentService.update(student);
        return "redirect:/students/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return "redirect:/students/all";
    }

    @GetMapping("/{id}")
    public String studentDetails(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "details";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "search";
    }

    @GetMapping("/search/result")
    public String searchByEmail(@RequestParam String email, Model model) {
        //Student student = studentService.findByEmail(email);
        Student student = studentService.getStudentByEmailAddress(email);
        model.addAttribute("student", student);
        return "details";
    }
}
