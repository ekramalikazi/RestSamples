package com.ekram.spring.controller.exception;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * In case of Exception, response object.
 * 
 * @author kazi_e
 *
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorMessage {
 
    private List<String> errors;
 
    public ErrorMessage() {
    }
 
    public ErrorMessage(List<String> errors) {
        this.errors = errors;
    }
 
    public ErrorMessage(String error) {
        this(Collections.singletonList(error));
    }
 
    public ErrorMessage(String ... errors) {
        this(Arrays.asList(errors));
    }
 
    public List<String> getErrors() {
        return errors;
    }
 
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}