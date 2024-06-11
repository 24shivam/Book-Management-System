package in.gfg.BookManagementSystem.repository;

import in.gfg.BookManagementSystem.model.Author;
import in.gfg.BookManagementSystem.model.Book;
import in.gfg.BookManagementSystem.model.BookType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book,Integer> {

    List<Book> findByBookNo(String bookNo);
    List<Book> findByAuthorName(String authorName);
    List<Book> findByCost(int cost);
    List<Book> findByCostLessThan(int cost);
    List<Book> findByType(BookType bookType);

    void deleteByBookNo(String bookNo);
}
