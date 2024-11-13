package com.boekhoud.backendboekhoudapplicatie.exception;

import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "Invalid Argument");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Handle DocumentException
    @ExceptionHandler(DocumentException.class)
    public ResponseEntity<Object> handleDocumentException(DocumentException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "PDF Generation Error");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle IOException
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleIOException(IOException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "IO Error");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "Server Error");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
