package in.gfg.BookManagementSystem.service;

import in.gfg.BookManagementSystem.exception.DataNotFound;
import in.gfg.BookManagementSystem.exception.TxnException;
import in.gfg.BookManagementSystem.model.FilterType;
import in.gfg.BookManagementSystem.model.Operators;
import in.gfg.BookManagementSystem.model.Student;
import in.gfg.BookManagementSystem.model.StudentType;
import in.gfg.BookManagementSystem.repository.StudentRedisRepo;
import in.gfg.BookManagementSystem.repository.StudentRepo;
import in.gfg.BookManagementSystem.requestDto.StudentCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService implements UserDetailsService {
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private StudentRedisRepo redisRepo;

    public Student createStudent(StudentCreateRequest studentCreateRequest) {
//avoid to add duplicate values of students
        List<Student> studentListFromDB =studentRepo.findByPhoneNo(studentCreateRequest.getPhoneNo());

        Student studentFromDB=null;

        if(studentListFromDB==null || studentListFromDB.isEmpty())
        {
          studentCreateRequest.setAuthority("USER");// setting student role
          studentFromDB=  studentRepo.save(studentCreateRequest.toStudent());
          return studentFromDB;
        }
        studentFromDB= studentListFromDB.get(0);

        // push the students data in redis and database as well
        redisRepo.setStudentToRedis(studentFromDB);
        return studentFromDB;


    }

    public List<Student> filter(FilterType filterBy, Operators operators, String value) throws DataNotFound {

        switch (operators){
            case EQUALS :
                switch (filterBy){
                    case STUDENT_NAME:
                        List<Student> studentList = redisRepo.getStudentByStudentName(value);
                        if (studentList != null && !studentList.isEmpty()) {
                            return studentList;
                        }
                        studentList = studentRepo.findByName(value);
                        if (!studentList.isEmpty()) {
                            redisRepo.setStudentToRedisByStudentName(studentList.get(0));
                            return studentList;
                        }
                        throw new DataNotFound("No students found with name: " + value);
                    case STUDENT_EMAIL:
                        List<Student> studentsByEmail = (List<Student>) studentRepo.findByEmail(value);
                        if (!studentsByEmail.isEmpty()) {
                            return studentsByEmail;
                        }
                        throw new DataNotFound("No students found with email: " + value);
                    case STUDENT_ADDRESS:
                        List<Student> studentsByAddress = studentRepo.findByAddress(value);
                        if (!studentsByAddress.isEmpty()) {
                            return studentsByAddress;
                        }
                        throw new DataNotFound("No students found with address: " + value);
                    case STUDENT_TYPE:
                        List<Student> studentsByType = studentRepo.findByType(StudentType.valueOf(value));
                        if (!studentsByType.isEmpty()) {
                            return studentsByType;
                        }
                        throw new DataNotFound("No students found with type: " + value);
                    case STUDENT_PHONE:
                        List<Student> studentsByPhone = studentRepo.findByPhoneNo(value);
                        if (!studentsByPhone.isEmpty()) {
                            return studentsByPhone;
                        }
                        throw new DataNotFound("No students found with phone number: " + value);

                }

            case IN:
                switch (filterBy){
                    case STUDENT_TYPE :
                        return studentRepo.findByType(StudentType.valueOf(value));
                }
            default:
                return  new ArrayList<>();
        }

    }

    @Override
    public UserDetails loadUserByUsername(String phoneNo) throws UsernameNotFoundException {
        if(! studentRepo.findByPhoneNo(phoneNo).isEmpty()){
            return studentRepo.findByPhoneNo(phoneNo).get(0);
        }
        return null;
    }
}
