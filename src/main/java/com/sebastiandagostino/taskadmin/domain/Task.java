package com.sebastiandagostino.taskadmin.domain;

import com.sebastiandagostino.taskadmin.util.MathUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Task implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    private static final Integer MINUTES_TO_SLEEP = 1;

    private static final Double FAILURE_PROBABILITY_THRESHOLD = 0.2;

    private Integer value;

    private Boolean prime;

    private TaskState taskState;

    public Task(int value) {
        this.value = value;
        this.taskState = TaskState.PENDING_EXECUTION;
    }

    public Integer getValue() {
        return value;
    }

    public synchronized Boolean getPrime() {
        return prime;
    }

    private synchronized void setPrime(Boolean prime) {
        this.prime = prime;
    }

    public synchronized TaskState getTaskState() {
        return taskState;
    }

    private synchronized void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    @Override
    public void run() {
        this.setTaskState(TaskState.EXECUTING);
        logger.info("Task " + Thread.currentThread().getName() + " starts to calculate isPrime for " + this.getValue());
        logger.info("Task " + Thread.currentThread().getName() + " changed state to: " + this.getTaskState());
        if (!MathUtil.passesProbability(FAILURE_PROBABILITY_THRESHOLD)) {
            this.setTaskState(TaskState.FAILED);
            logger.info("Task " + Thread.currentThread().getName() + " fails to calculate isPrime for " + this.getValue());
            logger.info("Task " + Thread.currentThread().getName() + " changed state to: " + this.getTaskState());
            return;
        }
        try {
            TimeUnit.MINUTES.sleep(MINUTES_TO_SLEEP);
            this.setPrime(MathUtil.isPrime(this.getValue()));
        } catch (InterruptedException e) {
            this.setTaskState(TaskState.FAILED);
            logger.info("Task " + Thread.currentThread().getName() + " was interrupted and unable to calculate isPrime for " + this.getValue());
            logger.info("Task " + Thread.currentThread().getName() + " changed state to: " + this.getTaskState());
        }
        this.setTaskState(TaskState.FINISHED);
        logger.info("Task " + Thread.currentThread().getName() + " finished calculation for " + this.getValue() + " and the value of isPrime is " + this.getPrime());
        logger.info("Task " + Thread.currentThread().getName() + " changed state to: " + this.getTaskState());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Task)) {
            return false;
        }
        Task other = (Task) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(value, other.value);
        builder.append(prime, other.prime);
        builder.append(taskState, other.taskState);
        return builder.isEquals();
    }

}
