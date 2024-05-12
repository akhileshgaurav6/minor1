package com.gfg.minor1.request;

import com.gfg.minor1.model.Student;
import com.gfg.minor1.model.StudentType;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateRequest {

    @Value("${student.authority}")
    private String studentAuthority;

    private String name;

    private String email;

    private String phoneNo;

    private String address;

    private String password;

    private String authority;

    public Student toStudent() {
        return Student.builder().name(this.name).
                        email(this.email).
                phoneNo(this.phoneNo).
                address(this.address).
                password(this.password).
                authority(this.authority).
                status(StudentType.ACTIVE).
                build();
    }
}
