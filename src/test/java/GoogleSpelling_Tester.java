
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
public class GoogleSpelling_Tester extends AbstractTester {

    public static void main(String[] args) {
        new GoogleSpelling_Tester().run();
    }

    @Override
    protected void preExecution() {
    }

    @Override
    protected void execution() {
        /*
        SpellChecker checker = new SpellChecker();

SpellResponse spellResponse = checker.check( "helloo worlrd" );

for( SpellCorrection sc : spellResponse.getCorrections() )
    System.out.println( sc.getValue() );
        */
    }

    @Override
    protected void posExecution() {
    }
}
