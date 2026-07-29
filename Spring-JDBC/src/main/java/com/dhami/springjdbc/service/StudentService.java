package com.dhami.springjdbc.service;

import com.dhami.springjdbc.model.Student;
import com.dhami.springjdbc.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void createStudent(Student student) {
        studentRepository.createStudent(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.getStudent();
    }

    public Student getStudentById(Long id) {
        return studentRepository.getStudentById(id);
    }

    public void updateStudent(Student student) {
        studentRepository.updateStudent(student, student.getId());
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteStudent(id);
    }
}