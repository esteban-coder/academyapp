package com.mitocode.exam.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDTO {

    private LocalDateTime datetime;
    private String message;
    private String details;

}
