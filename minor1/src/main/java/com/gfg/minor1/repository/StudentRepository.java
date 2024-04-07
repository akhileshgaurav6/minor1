package com.gfg.minor1.repository;

import com.gfg.minor1.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByPhoneNo(String phoneNo);
}
