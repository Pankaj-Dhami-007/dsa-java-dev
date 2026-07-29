package com.dhami.java_dev.aop.service;

import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService{

    public String createStudent(){

        //System.out.println("common logic ");

        System.out.println("Student saved ");
        return "Student saved";
    }
}
