package in.gfg.BookManagementSystem.repository;

import in.gfg.BookManagementSystem.model.Author;
import in.gfg.BookManagementSystem.model.Student;
import in.gfg.BookManagementSystem.model.StudentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {

    List<Student> findByPhoneNo (String phoneNo);

    List<Student> findByName(String name);
    Student findByEmail (String email);
    List<Student> findByAddress (String address);
    List<Student> findByType(StudentType studentType);

}
