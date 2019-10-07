
import com.makesystem.pidgey.console.old.base.Console__OLD;
import com.makesystem.pidgey.console.ConsoleColor;
import com.makesystem.pidgey.console.old.base.ConsoleValue;
import com.makesystem.pidgey.formatation.TimeFormat;
import com.makesystem.pidgey.tester.AbstractTester;
import com.makesystem.pidgey.thread.SchedulePool;
import com.makesystem.pidgey.thread.ThreadPool;
import com.makesystem.pidgey.thread.ThreadsHelper;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
        testThreadPool();
        //testSchedulePool();
    }

    void testThreadPool() {
        final ThreadPool pool = new ThreadPool();
        new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                new Thread(() -> pool.execute(toSimulate())).start();
                pool.execute(toSimulate());
            }
        }).start();
        System.out.println("Aguardando");
        pool.waitFinish();
        System.out.println("Terminou");
    }

    Runnable toSimulate() {
        return () -> {
            // Simula a execução de algo que leva 10ms
            ThreadsHelper.sleep(2);
        };
    }

    ScheduledFuture scheduleAtFixedRate;
    ScheduledFuture scheduleWithFixedDelay;

    void testSchedulePool() {
        printTime("Start at", ConsoleColor.BLACK);
        final SchedulePool schedulePool = new SchedulePool();
        schedulePool.schedule(()
                -> printTime("schedule", ConsoleColor.CYAN), 4, TimeUnit.SECONDS);

        final AtomicInteger countScheduleAtFixedRate = new AtomicInteger(0);
        scheduleAtFixedRate = schedulePool.scheduleAtFixedRate(() -> {
            printTime("scheduleAtFixedRate", ConsoleColor.YELLOW);
            if (countScheduleAtFixedRate.getAndIncrement() == 10) {
                scheduleAtFixedRate.cancel(true);
            }
        }, 5, 2, TimeUnit.SECONDS);

        final AtomicInteger countScheduleWithFixedDelay = new AtomicInteger(0);
        scheduleWithFixedDelay = schedulePool.scheduleWithFixedDelay(() -> {
            printTime("scheduleWithFixedDelay", ConsoleColor.PURPLE);
            if (countScheduleWithFixedDelay.getAndIncrement() == 15) {
                scheduleWithFixedDelay.cancel(true);
            }
        }, 5, 2, TimeUnit.SECONDS);
    }

    void printTime(final String title, final ConsoleColor color) {
        final long time = System.currentTimeMillis();
        final String timeFormated = TimeFormat.format(time, TimeFormat.Patterns.Built.DATE_FULL_TIME);
        Console__OLD.println(
                new ConsoleValue(title + ": ", 30, color),
                new ConsoleValue(timeFormated, ConsoleColor.BLUE),
                new ConsoleValue(" | " + time, ConsoleColor.BLACK));
    }

    @Override
    protected void posExecution() {
    }

}
