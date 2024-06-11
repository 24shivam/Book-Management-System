package in.gfg.BookManagementSystem.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Txn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "txn_id", length = 255)
    private String  txnId ;

    @JoinColumn
    @ManyToOne
    private Student student;

    @JoinColumn
    @ManyToOne
    private Book book;


    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;

    private int paidAmount;//taking some advance money to user

    @Enumerated(EnumType.STRING)
    private TxnStatus txnStatus;

}
