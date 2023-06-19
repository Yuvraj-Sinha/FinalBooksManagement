package com.trueid.errorhandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.trueid.exception.ApplicationError;
import com.trueid.exception.OrderNotPlaced;

@ControllerAdvice
@RestController
public class ErrorHandler extends ResponseEntityExceptionHandler {

	@Value("${api_doc_url}")
	private String details;

	@ExceptionHandler(OrderNotPlaced.class)
	public ResponseEntity<ApplicationError> handleCustomerNotFoundException(OrderNotPlaced exception,
			WebRequest webRequest) {
		ApplicationError error = new ApplicationError();
		error.setStatus(101);
		error.setMessage(exception.getMessage());
		error.setData(details);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

}
