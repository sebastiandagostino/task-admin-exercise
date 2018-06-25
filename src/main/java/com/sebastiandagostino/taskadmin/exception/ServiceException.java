package com.sebastiandagostino.taskadmin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Some constraints are violated.")
public class ServiceException extends Exception {

    public ServiceException() {
        super();
    }

}
