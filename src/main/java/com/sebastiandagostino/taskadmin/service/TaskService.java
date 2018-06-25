package com.sebastiandagostino.taskadmin.service;

import com.sebastiandagostino.taskadmin.domain.Task;
import com.sebastiandagostino.taskadmin.exception.ServiceException;

import java.util.List;

public interface TaskService {

    void addToPending(Task task) throws ServiceException;

    void dispatch(Task task) throws ServiceException;

    List<Task> findAll();

}
