package com.study.restwebservicetask.exception;

import com.study.restwebservicetask.exception.contact.ContactDoesNotExistException;
import com.study.restwebservicetask.exception.contact.ContactWithSameIdAlreadyExistException;
import com.study.restwebservicetask.exception.user.UserWithSameIdAlreadyExistException;
import com.study.restwebservicetask.exception.user.UserDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AwesomeExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserWithSameIdAlreadyExistException.class)
    protected ResponseEntity<AwesomeException> handleUserWithSameIdAlreadyExistException() {
        return new ResponseEntity<>(new AwesomeException("User with same id is already exist"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    protected ResponseEntity<AwesomeException> handleUserDoesNotExistException() {
        return new ResponseEntity<>(new AwesomeException("User with this id is not exist"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContactWithSameIdAlreadyExistException.class)
    protected ResponseEntity<AwesomeException> handleContactWithSameIdAlreadyExistException() {
        return new ResponseEntity<>(new AwesomeException("Contact with same id is already exist"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ContactDoesNotExistException.class)
    protected ResponseEntity<AwesomeException> handleContactDoesNotExistException() {
        return new ResponseEntity<>(new AwesomeException("Contact with this id is not exist"), HttpStatus.NOT_FOUND);
    }



    private static class AwesomeException {
        private String message;

        public AwesomeException(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
