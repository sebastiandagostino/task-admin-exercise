package com.sebastiandagostino.taskadmin.service.impl;

import com.sebastiandagostino.taskadmin.domain.Dispatcher;
import com.sebastiandagostino.taskadmin.domain.Task;
import com.sebastiandagostino.taskadmin.exception.ServiceException;
import com.sebastiandagostino.taskadmin.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private Dispatcher dispatcher;

    private ExecutorService executorService;

    public TaskServiceImpl() {
        this.dispatcher = new Dispatcher();
        this.executorService = Executors.newSingleThreadExecutor();
        this.executorService.execute(this.dispatcher);
    }

    @Override
    public void addToPending(Task task) throws ServiceException {
        if (task == null) {
            throw new ServiceException();
        }
        this.dispatcher.addToPending(task);
    }

    @Override
    public void dispatch(Task task) throws ServiceException {
        if (task == null) {
            throw new ServiceException();
        }
        this.dispatcher.dispatch(task);
    }

    @Override
    public List<Task> findAll() {
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(this.dispatcher.getPendingTasks());
        taskList.addAll(this.dispatcher.getWorkingTasks());
        taskList.addAll(this.dispatcher.getFinishedTasks());
        return taskList;
    }

}
