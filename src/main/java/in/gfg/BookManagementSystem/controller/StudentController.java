package in.gfg.BookManagementSystem.controller;

import in.gfg.BookManagementSystem.exception.DataNotFound;
import in.gfg.BookManagementSystem.model.*;
import in.gfg.BookManagementSystem.requestDto.StudentCreateRequest;
import in.gfg.BookManagementSystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @PostMapping("/create")
    public Student createStudent(@RequestBody StudentCreateRequest studentCreateRequest){
        return studentService.createStudent(studentCreateRequest);

    }

    @GetMapping("filterBy")
    public List<Student>filter(@RequestParam ("filterBy") FilterType filterBy,
                              @RequestParam ("Operator") Operators operators,
                              @RequestParam ("value") String value) throws DataNotFound {
        return studentService.filter(filterBy,operators, value);

    }


}
