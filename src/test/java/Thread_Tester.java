
import com.makesystem.pidgey.tester.AbstractTester;
import com.makesystem.pidgey.thread.SchedulePool;
import com.makesystem.pidgey.thread.ThreadPool;
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
        final ThreadPool pool = new ThreadPool();
        
        for(int i = 0; i < 1000000; i++){
            new Thread(() -> pool.execute(() -> {})).start();                        
            pool.execute(() -> {});
        } 
        
        //new SchedulePool().schedule(() -> {}, 1, TimeUnit.SECONDS);
    }

    @Override
    protected void posExecution() {
    }

}
