package com.apica_2.assignment.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException{
  private HttpStatus status;
  private String errorMessage;
}
