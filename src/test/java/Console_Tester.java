
import com.makesystem.pidgey.console.Console;
import com.makesystem.pidgey.console.ConsoleColor;
import com.makesystem.pidgey.monitor.Monitor;
import com.makesystem.pidgey.tester.AbstractTester;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author riche
 */
public class Console_Tester extends AbstractTester {

    public static void main(String[] args) {
        new Console_Tester().run();
    }
    
    @Override
    protected void preExecution() {
    }

    @Override
    protected void execution() {
        
        final String value = "{cc}{s}\t{cc}{i}\t{d}";

        final Object[][] values = {
            {"{ig}", "Nome", "{ig}", "{ig}Idade", "{ig}Data"},
            {ConsoleColor.BLUE, "Richeli Trindade de Vargas", ConsoleColor.RESET, 30, new Date()},
            {ConsoleColor.RED, "Patrícia Bays", ConsoleColor.RESET, 25, new Date()},
            {ConsoleColor.GREEN, "João", ConsoleColor.RESET, 5, new Date()}};

        Monitor.exec(() -> Console.log(value, "Richeli Trindade de Vargas", 30, new Date())).print();
        Monitor.exec(() -> Console.log(value, "Richeli Trindade de Vargas", 30, new Date())).print();
        Monitor.exec(() -> Console.log(value, "Richeli Trindade de Vargas", 30, new Date())).print();

        Monitor.exec(() -> Console.log(value, values)).print();
        Monitor.exec(() -> Console.log(value, values)).print();
        Monitor.exec(() -> Console.log(value, values)).print();

        Monitor.compare(
                () -> Console.log(value, values),
                () -> Console.log(value, values));
    }

    @Override
    protected void posExecution() {

    }

}
