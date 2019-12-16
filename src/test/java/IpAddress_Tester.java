
import com.makesystem.pidgey.console.Console;
import com.makesystem.pidgey.io.net.IpAddressJRE;
import com.makesystem.pidgey.tester.AbstractTester;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author riche
 */
public class IpAddress_Tester extends AbstractTester {

    private static final long serialVersionUID = -5988216934564565700L;

    public static void main(String[] args) {
        new IpAddress_Tester().run();
    }
    
    @Override
    protected void preExecution() {
    }

    @Override
    protected void execution() {
        
        try {
            final IpAddressJRE ipAddressJRE = new IpAddressJRE();
            Console.log("IP: {s}", ipAddressJRE.getPublic());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    @Override
    protected void posExecution() {

    }

}
