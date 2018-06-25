package com.sebastiandagostino.taskadmin.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dispatcher implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    public static final Integer MAX_THREADS = 3;

    private Boolean active;

    private ExecutorService executorService;

    private ConcurrentLinkedDeque<Task> pendingTasks;

    private ConcurrentLinkedDeque<Task> workingTasks;

    private ConcurrentLinkedDeque<Task> finishedTasks;

    public Dispatcher() {
        this.pendingTasks = new ConcurrentLinkedDeque<>();
        this.workingTasks = new ConcurrentLinkedDeque<>();
        this.finishedTasks = new ConcurrentLinkedDeque<>();
        this.executorService = Executors.newFixedThreadPool(MAX_THREADS);
        this.active = true;
    }

    public synchronized void addToPending(Task task) {
        logger.info("Add task to pending queue with value: " + task.getValue());
        this.pendingTasks.add(task);
    }

    public synchronized void dispatch(Task task) {
        logger.info("Dispatch new task with value: " + task.getValue());
        this.pendingTasks.removeIf(t -> t.equals(task));
        this.workingTasks.add(task);
    }

    /**
     * Stops the threads and the dispatcher run method immediately
     */
    public synchronized void stop() {
        this.active = false;
        this.executorService.shutdown();
    }

    public synchronized Boolean getActive() {
        return active;
    }

    @Override
    public void run() {
        while (getActive()) {
            if (this.workingTasks.isEmpty()) {
                continue;
            } else {
                Task task = this.workingTasks.poll();
                this.executorService.execute(task);
                this.finishedTasks.add(task);
            }
        }
    }

    public ConcurrentLinkedDeque<Task> getPendingTasks() {
        return pendingTasks;
    }

    public ConcurrentLinkedDeque<Task> getWorkingTasks() {
        return workingTasks;
    }

    public ConcurrentLinkedDeque<Task> getFinishedTasks() {
        return finishedTasks;
    }

}
