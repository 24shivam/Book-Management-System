package in.gfg.BookManagementSystem.requestDto;

import in.gfg.BookManagementSystem.model.Author;
import in.gfg.BookManagementSystem.model.Book;
import in.gfg.BookManagementSystem.model.BookType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCreateRequest {

    @NotBlank(message = "book name must not be blank ")
    private String name;

    @NotBlank(message = "book number must not be blank ")
    private String bookNo;
    @Positive(message = "cost should not be negative")
    private int cost;

    private BookType type;
    private String authorName;

    private String authorEmail;

    public Author toAuthor(){
        return Author.builder().
         name(this.authorName).
         email(this.authorEmail)
        .build();
    }

    public Book toBook(){
        return Book.builder()
                .name(this.name)
                .cost(this.cost)
                .bookNo(this.bookNo)
                .type(this.type)
                .build();
    }


}
