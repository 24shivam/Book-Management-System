package in.gfg.BookManagementSystem.service;

import in.gfg.BookManagementSystem.exception.DataNotFound;
import in.gfg.BookManagementSystem.model.*;
import in.gfg.BookManagementSystem.repository.AuthorRepo;
import in.gfg.BookManagementSystem.repository.BookRedisCacheRepo;
import in.gfg.BookManagementSystem.repository.BookRepo;
import in.gfg.BookManagementSystem.requestDto.BookCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private BookRedisCacheRepo cacheRepo;


    public Book createBook(BookCreateRequest bookCreateRequest){

        /* (condition) we can use @Query annotation to perform some condition
        * check if the author, which is coming to me from frontend is already present in my database or not if not present,
        * add author into db otherwise, I will not add one more row inside my table
        * */

         Author authorfromDB = authorRepo.findByEmail(bookCreateRequest.getAuthorEmail());

         if (authorfromDB==null){
             //create a row inside the author table
             authorfromDB= authorRepo.save(bookCreateRequest.toAuthor());
         }
         //create a row inside my table
        Book book= bookCreateRequest.toBook();
         book.setAuthor(authorfromDB);
         book=bookRepo.save(book);
         // push the data in redis and database as well

        cacheRepo.setBookToRedis(book);
        return book;
    }

    public List<Book> filter(FilterType filterBy, Operators operators, String value) throws DataNotFound {

        switch (operators)
        {
            case EQUALS :
                switch (filterBy){
                    case BOOK_NO :
                        List<Book> list = cacheRepo.getBookByBookNo(value);
                        if (list != null && !list.isEmpty()) {
                            return list;
                        }
                        list = bookRepo.findByBookNo(value);
                        if (!list.isEmpty()) {
                            cacheRepo.setBookToRedisByBookNo(list.get(0));
                            return list;
                        }
                        throw new DataNotFound("No books found with book number: " + value);
                        //return bookRepo.findByBookNo(value);
                    case AUTHOR_NAME:
                        List<Book> booksByAuthorName = bookRepo.findByAuthorName(value);
                        if (!booksByAuthorName.isEmpty()) {
                            return booksByAuthorName;
                        }
                        throw new DataNotFound("No books found with author name: " + value);
                      //  return  bookRepo.findByAuthorName(value);
                    case COST:

                        List<Book> booksByCost = bookRepo.findByCost(Integer.valueOf(value));
                        if (!booksByCost.isEmpty()) {
                            return booksByCost;
                        }
                        throw new DataNotFound("No books found with cost: " + value);
                       // return  bookRepo.findByCost(Integer.valueOf(value));
                    case BOOK_TYPE:
                        List<Book> booksByType = bookRepo.findByType(BookType.valueOf(value));
                        if (!booksByType.isEmpty()) {
                            return booksByType;
                        }
                        throw new DataNotFound("No books found with type: " + value);


                        //return  bookRepo.findByType(BookType.valueOf(value));//converting value is in string to bookType

            }
            case LESS_THAN:

                switch (filterBy){
                    case COST:
                        return  bookRepo.findByCostLessThan(Integer.valueOf(value));
                }

            case IN:
                switch (filterBy){
                    case BOOK_TYPE :
                        return bookRepo.findByType(BookType.valueOf(value));
                }
            default:
                return new ArrayList<>();
        }
    }

    public void saveUpdate(Book book){
        bookRepo.save(book);
    }

    public void deleteBook(int  id) {
        bookRepo.deleteById(id);
    }
}

