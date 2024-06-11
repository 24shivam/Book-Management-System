package in.gfg.BookManagementSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = TxnException.class )
    public ResponseEntity<Object> handle(TxnException txnException){
        return new ResponseEntity<>(txnException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value =  DataNotFound.class )
    public ResponseEntity<Object> notFoundData(DataNotFound notFoundDataExpection){
        return new ResponseEntity<>(notFoundDataExpection.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
