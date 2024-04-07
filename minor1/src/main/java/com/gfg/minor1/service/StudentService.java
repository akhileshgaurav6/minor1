package com.gfg.minor1.service;

import com.gfg.minor1.model.*;
import com.gfg.minor1.repository.StudentRepository;
import com.gfg.minor1.request.StudentCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(StudentCreateRequest studentCreateRequest) {
        List<Student> studentList = studentRepository.findByPhoneNo(studentCreateRequest.getPhoneNo());
        Student studentFromDB = null;
        if (studentList == null || studentList.isEmpty()) {
            studentFromDB = studentRepository.save(studentCreateRequest.toStudent());
            return studentFromDB;
        }
        studentFromDB = studentList.get(0);
        return studentFromDB;

    }

    public List<Student> filter(StudentFilterType filterBy, Operator operator, String value) {
        switch (operator) {
            case EQUALS:
                switch (filterBy) {
                    case CONTACT:
                        return studentRepository.findByPhoneNo(value);
                }
            default:
                return new ArrayList<>();
        }
    }
}
