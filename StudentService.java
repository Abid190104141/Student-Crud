package com.example.student.service;

import com.example.student.entity.Student;

import java.util.List;

public interface StudentService {
    Student save(Student student);

    List<Student> findAll();

    Student findById(Long id);

    Student findByEmail(String email);

    Student getStudentByEmailAddress(String emailAddress);

    Student update(Student student);

    void deleteById(Long id);
}
