package us.teamronda.briscola.utils;

import lombok.experimental.UtilityClass;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Small utility class to manage timer tasks.
 * If a {@link Timer} it is not cancelled, it will prevent the JVM from shutting down.
 * <p>
 * This class provides a program-wide timer to schedule tasks and offers
 * a method to terminate it when the shutdown is requested.
 */
@UtilityClass
public class TimerUtils {

    /*
    Settings Thread#isDaemon to true will allow the JVM to exit
    even if the thread is still running.
     */
    private final Timer timer = new Timer(true);

    /**
     * Schedule a task to run after a delay.
     *
     * @param task  the task to run
     * @param delay the delay in milliseconds
     */
    public void schedule(Runnable task, long delay) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, delay);
    }
}
