package in.gfg.BookManagementSystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity

public class Student implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String  name ;
    @Column(length = 30,unique = true)
    private String email;

    @Column(length = 15,unique = true,nullable = false)
    private String phoneNo;

    @Enumerated(EnumType.STRING)
    private StudentType type;

    private String address;

    private String password;

    private String authority;

    @CreationTimestamp
    private Date createOn;

    @UpdateTimestamp
    private  Date updatedOn;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<Book> bookList;


    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<Txn> tnxList;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authority.split(",")).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phoneNo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
