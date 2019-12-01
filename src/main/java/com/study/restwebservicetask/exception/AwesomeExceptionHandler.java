package com.study.restwebservicetask.exception;

import com.study.restwebservicetask.exception.contact.ContactDoesNotExistException;
import com.study.restwebservicetask.exception.contact.ContactNotFoundException;
import com.study.restwebservicetask.exception.contact.ContactWithSameIdAlreadyExistException;
import com.study.restwebservicetask.exception.user.UserNotFoundException;
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
    protected ResponseEntity<ErrorResponse> handleUserWithSameIdAlreadyExistException() {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "User with same id is already exist"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    protected ResponseEntity<ErrorResponse> handleUserDoesNotExistException() {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                "User with this id is not exist"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContactWithSameIdAlreadyExistException.class)
    protected ResponseEntity<ErrorResponse> handleContactWithSameIdAlreadyExistException() {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "Contact with same id is already exist"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ContactDoesNotExistException.class)
    protected ResponseEntity<ErrorResponse> handleContactDoesNotExistException() {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                "Contact with this id is not exist"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleUserNotFoundException() {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                "User not found"), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ContactNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleContactNotFoundException() {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                "Contact not found"), HttpStatus.NOT_FOUND);
    }


    private static class ErrorResponse {
        private int errorCode;
        private String message;

        public ErrorResponse(int errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }
    }
}
