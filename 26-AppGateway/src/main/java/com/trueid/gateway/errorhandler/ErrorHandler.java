/*
 * package com.trueid.gateway.errorhandler;
 * 
 * import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.http.codec.HttpMessageWriter; import
 * org.springframework.web.bind.annotation.ControllerAdvice; import
 * org.springframework.web.bind.annotation.ExceptionHandler; import
 * org.springframework.web.bind.annotation.RestController; import
 * org.springframework.web.context.request.WebRequest; import
 * org.springframework.web.reactive.accept.RequestedContentTypeResolver; import
 * org.springframework.web.reactive.result.method.annotation.
 * ResponseEntityResultHandler;
 * 
 * import com.trueid.gateway.exception.ApplicationError; import
 * com.trueid.gateway.exception.UnAuthorizedException;
 * 
 * @ControllerAdvice
 * 
 * @RestController public class ErrorHandler extends ResponseEntityResultHandler
 * {
 * 
 * public ErrorHandler(List<HttpMessageWriter<?>> writers,
 * RequestedContentTypeResolver resolver) { super(writers, resolver);
 * 
 * }
 * 
 * @Value("${api_doc_url}") private String details;
 * 
 * @ExceptionHandler(UnAuthorizedException.class) public
 * ResponseEntity<ApplicationError>
 * handleCustomerNotFoundException(UnAuthorizedException exception, WebRequest
 * webRequest) { ApplicationError error = new ApplicationError();
 * error.setStatus(101); error.setMessage(exception.getMessage());
 * error.setData(details); return new ResponseEntity<>(error,
 * HttpStatus.NOT_FOUND); }
 * 
 * }
 */