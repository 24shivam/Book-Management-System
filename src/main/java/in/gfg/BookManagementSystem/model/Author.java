package in.gfg.BookManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Builder
@Entity

public class Author implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String  name ;

    @Column(length = 15,unique = true,nullable = false)
    private String email;

    @CreationTimestamp
    private Date createOn;

    @UpdateTimestamp
    private  Date updatedOn;

    @OneToMany(mappedBy = "author")
    //JsonIgnore
    private List<Book> bookList;



}
