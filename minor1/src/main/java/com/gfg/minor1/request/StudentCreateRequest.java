package com.gfg.minor1.request;

import com.gfg.minor1.model.Student;
import com.gfg.minor1.model.StudentType;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateRequest {

    private String name;

    private String email;

    private String phoneNo;

    private String address;

    public Student toStudent() {
        return Student.builder().name(this.name).
                        email(this.email).
                phoneNo(this.phoneNo).
                address(this.address).
                status(StudentType.ACTIVE).
                build();
    }
}
