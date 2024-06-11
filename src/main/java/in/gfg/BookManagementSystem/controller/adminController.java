package in.gfg.BookManagementSystem.controller;

import in.gfg.BookManagementSystem.exception.TxnException;
import in.gfg.BookManagementSystem.model.Student;
import in.gfg.BookManagementSystem.requestDto.StudentCreateRequest;
import in.gfg.BookManagementSystem.service.AdminService;
import in.gfg.BookManagementSystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private AdminService adminService;
    @PostMapping("/create")
    public Student createAdmin(@RequestBody StudentCreateRequest studentCreateRequest) throws TxnException {
        // put validation, student phone is blank throw an exception
        if(StringUtils.isEmpty(studentCreateRequest.getPhoneNo())){
            throw new TxnException("student phone no can not be null.");
        }
        return adminService.createAdmin(studentCreateRequest);
    }
}
