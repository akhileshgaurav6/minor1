package com.gfg.minor1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TxnCreateRequest {

    @NotBlank(message = "student contact should not be blank")
    private String studentContact;

    @NotBlank(message = "book no should not be blank")
    private String bookNo;

    @Positive(message = "Amount should be positive")
    private Integer amount;

    // 1) you can create your own annotation
// 2) you can have some if else in controller


//1) have to make an annotation by urself
// 2) apply all the validation inside the project, add more exceptionhandler to the exception handler class.
}
