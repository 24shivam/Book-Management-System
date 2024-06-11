package in.gfg.BookManagementSystem.requestDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TxnReturnRequest {

    private String phoneNo;
    private String bookNo;
    private String txnId;


}
