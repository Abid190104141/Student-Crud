package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;
import com.example.student.exception.StudentNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student save(Student student) {

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException(
                    "A student with this email already exists.");
        }
        return studentRepository.save(student);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with id: " + id));
    }

    @Override
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with email: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentByEmailAddress(String emailAddress) {
        return studentRepository.GetStudentByEmail(emailAddress);
    }

    @Override
    public Student update(Student student) {
        Student existingStudent = findById(student.getId());
        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteById(Long id) {

        Student student = findById(id);

        studentRepository.delete(student);
    }

}