package com.sebastiandagostino.taskadmin.controller;

import com.sebastiandagostino.taskadmin.controller.dto.TaskDTO;
import com.sebastiandagostino.taskadmin.controller.mapper.TaskMapper;
import com.sebastiandagostino.taskadmin.exception.ServiceException;
import com.sebastiandagostino.taskadmin.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "v1/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public @ResponseBody
    List<TaskDTO> getAllTasks() {
        return TaskMapper.makeTaskDTOList(taskService.findAll());
    }

    @PostMapping("/pending")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTaskToPendingQueue(@Valid @RequestBody TaskDTO taskDTO) throws ServiceException {
        taskService.addToPending(TaskMapper.makeTask(taskDTO));
    }

    @PostMapping("/working")
    public void addTaskToWorkingQueue(@Valid @RequestBody TaskDTO taskDTO) throws ServiceException {
        taskService.dispatch(TaskMapper.makeTask(taskDTO));
    }

}
