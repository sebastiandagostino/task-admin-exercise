package com.sebastiandagostino.taskadmin.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class DispatcherTest {

    private static final int AWAIT_TERMINATION_TIMEOUT_IN_MINUTES = 2;

    private static final int TASK_AMOUNT = 5;

    private static final int INTEGER_BOUND = 100;

    @Test
    public void testRun() throws InterruptedException {
        Dispatcher dispatcher = new Dispatcher();
        TimeUnit.SECONDS.sleep(1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(dispatcher);
        List<Task> taskList = buildTaskList(TASK_AMOUNT);

        taskList.stream().forEach(task -> {
            dispatcher.dispatch(task);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                fail();
            }
        });

        executorService.awaitTermination(AWAIT_TERMINATION_TIMEOUT_IN_MINUTES, TimeUnit.MINUTES);
        assertEquals(TASK_AMOUNT, dispatcher.getFinishedTasks().size());
        dispatcher.getFinishedTasks().stream().forEach(task -> assertTrue(task.getTaskState() == TaskState.FAILED || task.getTaskState() == TaskState.FINISHED));
    }

    private static List<Task> buildTaskList(int amount) {
        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            taskList.add(new Task(ThreadLocalRandom.current().nextInt(2, INTEGER_BOUND)));
        }
        return taskList;
    }

}
