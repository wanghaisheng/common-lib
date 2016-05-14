package com.clear.bootest;

import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2015/10/9.
 */
public class PersonTest {

    @Test
    public void testPerson() {
        ValidationUtils.rejectIfEmptyOrWhitespace(new Errors() {
            @Override
            public String getObjectName() {
                return null;
            }

            @Override
            public void setNestedPath(String nestedPath) {

            }

            @Override
            public String getNestedPath() {
                return null;
            }

            @Override
            public void pushNestedPath(String subPath) {

            }

            @Override
            public void popNestedPath() throws IllegalStateException {

            }

            @Override
            public void reject(String errorCode) {

            }

            @Override
            public void reject(String errorCode, String defaultMessage) {

            }

            @Override
            public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {

            }

            @Override
            public void rejectValue(String field, String errorCode) {

            }

            @Override
            public void rejectValue(String field, String errorCode, String defaultMessage) {

            }

            @Override
            public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {

            }

            @Override
            public void addAllErrors(Errors errors) {

            }

            @Override
            public boolean hasErrors() {
                return false;
            }

            @Override
            public int getErrorCount() {
                return 0;
            }

            @Override
            public List<ObjectError> getAllErrors() {
                return null;
            }

            @Override
            public boolean hasGlobalErrors() {
                return false;
            }

            @Override
            public int getGlobalErrorCount() {
                return 0;
            }

            @Override
            public List<ObjectError> getGlobalErrors() {
                return null;
            }

            @Override
            public ObjectError getGlobalError() {
                return null;
            }

            @Override
            public boolean hasFieldErrors() {
                return false;
            }

            @Override
            public int getFieldErrorCount() {
                return 0;
            }

            @Override
            public List<FieldError> getFieldErrors() {
                return null;
            }

            @Override
            public FieldError getFieldError() {
                return null;
            }

            @Override
            public boolean hasFieldErrors(String field) {
                return false;
            }

            @Override
            public int getFieldErrorCount(String field) {
                return 0;
            }

            @Override
            public List<FieldError> getFieldErrors(String field) {
                return null;
            }

            @Override
            public FieldError getFieldError(String field) {
                return null;
            }

            @Override
            public Object getFieldValue(String field) {
                return null;
            }

            @Override
            public Class<?> getFieldType(String field) {
                return null;
            }
        }, "name", "null is fied");
    }

}