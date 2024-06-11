package in.gfg.BookManagementSystem.service;

import in.gfg.BookManagementSystem.model.Student;
import in.gfg.BookManagementSystem.repository.StudentRedisRepo;
import in.gfg.BookManagementSystem.repository.StudentRepo;
import in.gfg.BookManagementSystem.requestDto.StudentCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private StudentRepo studentRepository;



    public Student createAdmin(StudentCreateRequest studentCreateRequest) {
        List<Student> studentList = studentRepository.findByPhoneNo(studentCreateRequest.getPhoneNo());
        Student studentFromDB= null;
        if(studentList == null || studentList.isEmpty()){
            studentCreateRequest.setAuthority("ADMIN");// setting admin role
            studentFromDB = studentRepository.save(studentCreateRequest.toStudent());
            return studentFromDB;
        }
        studentFromDB = studentList.get(0);
        return studentFromDB;

    }
}