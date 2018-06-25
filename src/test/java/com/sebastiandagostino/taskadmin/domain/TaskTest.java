package com.sebastiandagostino.taskadmin.domain;

import com.sebastiandagostino.taskadmin.util.MathUtil;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class TaskTest {

    @Test
    public void testNewTaskIsAlwaysPending() {
        Task task = new Task(ThreadLocalRandom.current().nextInt());
        assertEquals(TaskState.PENDING_EXECUTION, task.getTaskState());
    }

    @Test
    public void testEquals() {
        Integer value = 2;
        Task task1 = new Task(value);
        Task task2 = new Task(value);

        assertEquals(task1, task2);
    }

    @Test
    public void testNotEquals() {
        Integer value1 = 2;
        Integer value2 = 3;
        Task task1 = new Task(value1);
        Task task2 = new Task(value2);

        assertNotEquals(task1, task2);
    }

    @Test
    public void testRun() throws InterruptedException {
        Integer value = 7;
        Task task = new Task(value);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task);

        executorService.awaitTermination(2, TimeUnit.MINUTES);
        if (task.getTaskState() == TaskState.FINISHED) {
            assertEquals(MathUtil.isPrime(value), task.getPrime());
        } else if (task.getTaskState() == TaskState.FAILED) {
            // Recursive call in case probability changes the task to FAILED
            testRun();
        } else {
            fail();
        }
    }

}
