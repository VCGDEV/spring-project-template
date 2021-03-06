package com.vcgdev.demo.rest.exception;
import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import com.vcgdev.common.exception.ErrorCode;
import com.vcgdev.common.exception.ErrorDetail;
import com.vcgdev.common.exception.ServiceException;
import com.vcgdev.common.exception.web.ApiError;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Primary
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(ServiceException.class)
	public ResponseEntity<ApiError> handleServiceException(ServiceException exception) {
		ApiError apiError = new ApiError(exception.getCode());
		apiError.setErrors(exception.getErrors());
		HttpStatus status = HttpStatus.valueOf(Integer.parseInt(apiError.getCode()));
		return new ResponseEntity<>(apiError, status);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException cve) {
		ApiError apiError = new ApiError(ErrorCode.BAD_REQUEST);
		Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
		
		violations.stream()
			.forEach(cv-> addError(ValidationError.parse(cv.getMessage()), apiError));
		
		sortApiErrors(apiError);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException ex, 
		HttpHeaders headers, 
		HttpStatus status, 
		WebRequest request) {
		
		ApiError apiError = new ApiError(ErrorCode.BAD_REQUEST);
		
		ex.getBindingResult().getFieldErrors()
			.stream()
			.forEach(error -> addError(ValidationError.parse(error.getDefaultMessage()), apiError));
		sortApiErrors(apiError);
		
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}
	
	private void sortApiErrors(ApiError error) {
		Collections.sort(error.getErrors(), (e1, e2) -> e1.getCode().compareTo(e2.getCode()));
	}
	
	private void addError(ValidationError customError, ApiError apiError) {
		if(customError != null) {
			apiError.addError(new ErrorDetail(customError.getCode(), customError.getMessage()));
		}
	}
}
