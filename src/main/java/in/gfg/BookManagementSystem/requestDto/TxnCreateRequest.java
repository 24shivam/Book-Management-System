package in.gfg.BookManagementSystem.requestDto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TxnCreateRequest {


    @NotBlank(message = "book number must not be blank ")
    private String bookNo;

    @Positive(message = "amount should be positive ")
    private Integer amount;


}
