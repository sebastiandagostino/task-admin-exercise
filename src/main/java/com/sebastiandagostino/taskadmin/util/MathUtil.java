package com.sebastiandagostino.taskadmin.util;

import org.apache.commons.lang3.Validate;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class MathUtil {

    public static Boolean isPrime(long number) {
        return number > 1 && LongStream.rangeClosed(2, (long) Math.sqrt(number)).noneMatch(div -> number % div == 0);
    }

    public static Boolean passesProbability(double probability) {
        Validate.isTrue(0 <= probability && probability < 1);
        return (ThreadLocalRandom.current().nextDouble() > probability);
    }

}
