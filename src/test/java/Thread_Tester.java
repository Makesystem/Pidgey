
import com.makesystem.pidgey.console.Console;
import com.makesystem.pidgey.monitor.Monitor;
import com.makesystem.pidgey.tester.AbstractTester;
import com.makesystem.pidgey.thread.Executors;
import com.makesystem.pidgey.thread.ThreadPoolExecutor;
import com.makesystem.pidgey.thread.ThreadsHelper;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Richeli.vargas
 */
public class Thread_Tester extends AbstractTester {

    public static void main(String[] args) {
        new Thread_Tester().run();
    }

    @Override
    protected void preExecution() {

    }

    @Override
    protected void execution() {
        waitTermination();
        //fixed();
        //adaptive();
        //schedule();
    }

    void fixed() {

        final ExecutorService executor = Executors.Fixed.create(40, "Pool for tests");

        executor.submit(() -> Console.log("test"));
        executor.submit(() -> Console.log("test"));
        executor.submit(() -> Console.log("test"));
    }

    void adaptive() {

        final ExecutorService executor = Executors.Adaptive.create(40, "Adaptive for tests");

        executor.submit(() -> Console.log("test"));
        executor.submit(() -> Console.log("test"));
        executor.submit(() -> Console.log("test"));
        executor.submit(() -> Console.log("test"));
    }

    void schedule() {

        final ScheduledExecutorService schedule = Executors.Schedule.create("Schedule Test");

        schedule.schedule(() -> Console.log("schedue...."), 5, TimeUnit.SECONDS);
        schedule.scheduleAtFixedRate(() -> Console.log("schedule at fixed rate...."), 1, 2, TimeUnit.SECONDS);

    }

    Collection<Callable<String>> callables(final int threads, final int delay) {
        final Collection<Callable<String>> callables = new LinkedList<>();

        for (int i = 0; i < threads; i++) {
            callables.add(() -> {
                ThreadsHelper.sleep(delay);
                return "";
            });
        }

        return callables;
    }

    void waitTermination() {

        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        final int threads = availableProcessors * 100;
        final int delay = 1000;

        final ThreadPoolExecutor executor = Executors.Fixed.create(threads, "Pool for tests");

        final Collection<Callable<String>> callables = callables(threads, delay);

        try {
            
            // It is to start all threads
            executor.invokeAll(callables);

            Monitor.exec("duration... ", () -> {
                executor.invokeAll(callables);
            }).print();
            
        } catch (Throwable ex) {
        } finally {
            executor.shutdown();
        }
    }

    @Override
    protected void posExecution() {
    }

}
