package com.example.INVENTARIO_DROGUERIA.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(NotFoundDataException.class)
    public ResponseEntity<Object> handleNotFoundException (NotFoundDataException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMensaje());
    }

    @ExceptionHandler(NoContentData.class)
    public ResponseEntity<Object> handleNoContent (NoContentData e) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMensaje());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest (BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMensaje());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la ejecucion");
    }
}
