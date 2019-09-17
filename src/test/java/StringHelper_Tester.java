
import com.makesystem.pidgey.lang.StringHelper;
import com.makesystem.pidgey.tester.AbstractTester;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Richeli.vargas
 */
public class StringHelper_Tester extends AbstractTester {

    public static void main(String[] args) {
        new StringHelper_Tester().run();
    }

    @Override
    protected void preExecution() {
    }

    @Override
    protected void execution() {
        System.out.println(StringHelper.stripAccents("ÁÉÍÓÚÇ áéíóúç"));
        System.out.println(StringHelper.stripSpecialChars("123 AEIOUÇ aeiouç ÁÉÍÓÚÇ áéíóúç ;.,-_=+§'\"\\!@#$%¨&*()¹²³£¢¬´`~^[{ª]}º<,>.:;?/°|"));
        System.out.println(StringHelper.stripSpecialChars("123 AEIOUÇ aeiouç ÁÉÍÓÚÇ áéíóúç ;.,-_=+§'\"\\!@#$%¨&*()¹²³£¢¬´`~^[{ª]}º<,>.:;?/°|", " "));
        System.out.println(StringHelper.stripDifferentOf("123456789", "123"));
        System.out.println(StringHelper.appendAtStart("123", "0", 6));
        System.out.println(StringHelper.appendAtEnd("123", "0", 6));
    }

    @Override
    protected void posExecution() {
    }

}
