package com.sebastiandagostino.taskadmin.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sebastiandagostino.taskadmin.domain.TaskState;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDTO {

    private Integer value;

    private Boolean prime;

    @JsonSerialize(using = ToStringSerializer.class)
    private TaskState taskState;

    public TaskDTO() {
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Boolean getPrime() {
        return prime;
    }

    public void setPrime(Boolean prime) {
        this.prime = prime;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

}
