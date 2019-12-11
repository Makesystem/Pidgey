
import com.makesystem.pidgey.console.Console;
import com.makesystem.pidgey.console.ConsoleColor;
import com.makesystem.pidgey.console.ConsoleFlag;
import com.makesystem.pidgey.lang.StringHelper;
import com.makesystem.pidgey.tester.AbstractTester;
import com.makesystem.pidgey.thread.SchedulePool;
import com.makesystem.pidgey.thread.ThreadPool;
import com.makesystem.pidgey.thread.ThreadsHelper;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

        final ExecutorService service = Executors.newFixedThreadPool(10);
        final Collection<Callable<String>> callables = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            callables.add(() -> {
                Thread.sleep(5000);
                return "thread x";
            });

        }
        try {
            Console.log("ali");
            service.invokeAll(callables);
            Console.log("aqui");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void execution() {
        //testThreadPool();
        testSchedulePool();
    }

    void testThreadPool() {
        final ThreadPool pool = new ThreadPool();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
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
        final String titleMinLength = StringHelper.appendAtEnd(title, StringHelper.SPACE, 30);
        final long time = System.currentTimeMillis();

        final String format = new StringBuilder()
                .append(ConsoleFlag.COLOR.getFlag())
                .append(ConsoleFlag.TEXT.getFlag())
                .append(StringHelper.TB)
                .append(ConsoleFlag.COLOR.getFlag())
                .append(ConsoleFlag.DATE_TIME.getFlag())
                .toString();

        Console.log(format, color, titleMinLength, ConsoleColor.BLUE, time);
    }

    @Override
    protected void posExecution() {
    }

}
