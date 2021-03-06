package com.ekram.spring.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Duplicate User Exception.
 * 
 * @author kazi_e
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateUserException extends RuntimeException {

	private static final long serialVersionUID = -384231196788204513L;

}
