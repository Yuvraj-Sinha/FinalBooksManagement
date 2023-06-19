package com.sulzer.bookstore.errorhandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sulzer.bookstore.service.exceptions.ApplicationError;
import com.sulzer.bookstore.service.exceptions.BadRequestException;
import com.sulzer.bookstore.service.exceptions.BookNotFoundException;

@ControllerAdvice
@RestController
public class ErrorHandler extends ResponseEntityExceptionHandler {

	@Value("${api_doc_url}")
	private String details;

	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<ApplicationError> handleCustomerNotFoundException(BookNotFoundException exception,
			WebRequest webRequest) {
		ApplicationError error = new ApplicationError();
		error.setStatus(101);
		error.setMessage(exception.getMessage());
		error.setData(details);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApplicationError> handleCustomerNotFoundException(BadRequestException exception,
			WebRequest webRequest) {
		ApplicationError error = new ApplicationError();
		error.setStatus(101);
		error.setMessage(exception.getMessage());
		error.setData(details);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

}
