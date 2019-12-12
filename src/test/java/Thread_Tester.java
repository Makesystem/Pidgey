
import com.makesystem.pidgey.console.Console;
import com.makesystem.pidgey.tester.AbstractTester;
import com.makesystem.pidgey.thread.Executors;
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
        fixed();
        //adaptive();
        //schedule();
    }

    void fixed() {
        
        final ExecutorService executor = Executors.Fixed.create(40, "Pool for tests");

        executor.submit(() -> Console.log("test"));
        executor.submit(() -> Console.log("test"));
        executor.submit(() -> Console.log("test"));
        executor.submit(() -> Console.log("test"));
    }

    void adaptive() {

        final ExecutorService executor = Executors.Adaptive.create(40, "Pool for tests");

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

    @Override
    protected void posExecution() {
    }

}
