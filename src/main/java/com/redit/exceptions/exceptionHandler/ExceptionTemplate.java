package com.redit.exceptions.exceptionHandler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionTemplate {
    private int status;
    private String message;
    private long timstamp;
}
