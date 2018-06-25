package com.sebastiandagostino.taskadmin.controller.mapper;

import com.sebastiandagostino.taskadmin.controller.dto.TaskDTO;
import com.sebastiandagostino.taskadmin.domain.Task;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    public static Task makeTask(TaskDTO taskDTO) {
        return new Task(taskDTO.getValue());
    }

    public static TaskDTO makeTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setValue(task.getValue());
        taskDTO.setPrime(task.getPrime());
        taskDTO.setTaskState(task.getTaskState());
        return taskDTO;
    }

    public static List<TaskDTO> makeTaskDTOList(Collection<Task> taskCollection) {
        return taskCollection.stream()
                .map(TaskMapper::makeTaskDTO)
                .collect(Collectors.toList());
    }

}
