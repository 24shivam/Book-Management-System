package in.gfg.BookManagementSystem.controller;

import in.gfg.BookManagementSystem.exception.DataNotFound;
import in.gfg.BookManagementSystem.exception.TxnException;
import in.gfg.BookManagementSystem.model.Student;
import in.gfg.BookManagementSystem.requestDto.TxnCreateRequest;
import in.gfg.BookManagementSystem.requestDto.TxnReturnRequest;
import in.gfg.BookManagementSystem.service.TxnService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/txn")
public class TxnController {

    @Autowired
    private TxnService txnService;

    @PostMapping("/create")//user and admin both can access this api
    public String createTxn (@RequestBody @Valid TxnCreateRequest txnCreateRequest) throws TxnException, DataNotFound {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student =  (Student) authentication.getPrincipal();

        return "traction id :"+ txnService.create(txnCreateRequest,student);
    }

    @PutMapping("/return")// only admin can access this api
    public int returnTxn (@RequestBody TxnReturnRequest txnReturnRequest ) throws TxnException, DataNotFound {
        return txnService.returnBook(txnReturnRequest);
    }

}
