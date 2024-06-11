package in.gfg.BookManagementSystem.controller;

import in.gfg.BookManagementSystem.exception.DataNotFound;
import in.gfg.BookManagementSystem.model.Book;
import in.gfg.BookManagementSystem.model.FilterType;
import in.gfg.BookManagementSystem.model.Operators;
import in.gfg.BookManagementSystem.requestDto.BookCreateRequest;
import in.gfg.BookManagementSystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/create")
    public Book createBook(@RequestBody @Valid BookCreateRequest  bookCreateRequest){
       return bookService.createBook(bookCreateRequest);

    }
/*
* basically here instead of creating multiple get api for fetching the data I'll try to create only
* one  method which named as filter and annotated by the GetMapping and in this method we have
* two operands and operator which compares the values between two operands (like as 1st is book
* greater the 20000rs etc. ).
* */
    @GetMapping("/filterBy")
    public List<Book> filter(@RequestParam ("filterBy") FilterType filterBy,
                             @RequestParam ("Operator")Operators operators,
                             @RequestParam ("value") String value) throws DataNotFound {
        return bookService.filter(filterBy,operators, value);

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int  id){

        bookService.deleteBook(id);
        return ResponseEntity.ok("User with ID " + id + " has been deleted");
    }
}
