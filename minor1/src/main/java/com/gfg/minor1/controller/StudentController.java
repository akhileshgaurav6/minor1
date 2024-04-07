package com.gfg.minor1.controller;

import com.gfg.minor1.model.Student;
import com.gfg.minor1.request.StudentCreateRequest;
import com.gfg.minor1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/create")
    public Student createStudent(@RequestBody StudentCreateRequest studentCreateRequest){
       return studentService.createStudent(studentCreateRequest);

    }

}
