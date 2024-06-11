package in.gfg.BookManagementSystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity

public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String  name ;

    private String bookNo;

    private int cost;

    @Enumerated(EnumType.STRING)
    private  BookType type;

    @ManyToOne
    @JoinColumn //this represents the foreign key in table
    private Student student;


    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("bookList")//CHECK THIS
    private Author author;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<Txn> txnList;








}
