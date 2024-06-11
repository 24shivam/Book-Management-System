package in.gfg.BookManagementSystem.requestDto;

import in.gfg.BookManagementSystem.model.Student;
import in.gfg.BookManagementSystem.model.StudentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentCreateRequest {


    private String  name ;
    private String email;
    private String phoneNo;
    private StudentType type;
    private String password;
    private String authority;
    private String address;

    public Student toStudent(){
        return Student.builder()
                .name(this.name)
                .email(this.email)
                .phoneNo(this.phoneNo)
                .type(this.type)
                .password(this.password)
                .authority(this.authority)
                .address(this.address)
                .build();
    }

}
