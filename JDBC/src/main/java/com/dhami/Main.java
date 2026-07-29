package com.dhami;

import com.dhami.repository.StudentRepository;

public class Main {
    public static void main(String[] args) {

        StudentRepository studentRepository = new StudentRepository();

        //studentRepository.createStudent(new Student("Rohan", "rohan@gmail.com", 21));

//        studentRepository.updateStudent(
//                new Student("Rohit Negi", "rohit@gmail.com", 26), 8L);

        //studentRepository.deleteStudent(9L);

        studentRepository.getStudent();

    }
    }
