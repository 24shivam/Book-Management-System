package in.gfg.BookManagementSystem.repository;

import in.gfg.BookManagementSystem.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends JpaRepository<Author,Integer> {

    // 1-mysql style means running the query
    @Query(value = "select * from author where email=:email",nativeQuery = true)// what is native query
    public Author getAuthor(String email );


    // 2-hibernate  style means running the query
    @Query(value = "select a from Author a where a.email=:email",nativeQuery = false)// what is native query
    public Author getAuthorWithNativeMethod (String email );

    // 3- ways of writing the query
    Author findByEmail(String email );
}
