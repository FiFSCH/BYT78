package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
    Currency SEK, DKK, EUR; //deleted NOK because it is not initialized

    @Before
    public void setUp() throws Exception {
        /* Setup currencies with exchange rates */
        SEK = new Currency("SEK", 0.15);
        DKK = new Currency("DKK", 0.20);
        EUR = new Currency("EUR", 1.5);
    }

    @Test
    public void testGetName() {
        assertEquals("SEK", SEK.getName());
        assertEquals("DKK", DKK.getName());
        assertEquals("EUR", EUR.getName());
    }

    @Test
    public void testGetRate() {
        assertEquals(0.15, SEK.getRate(), 0); //added delta = 0 because I don't accept any error margin
        assertEquals(0.20, DKK.getRate(), 0);
        assertEquals(1.5, EUR.getRate(), 0);
    }

    @Test
    public void testSetRate() {
        SEK.setRate(0.4);
        assertEquals(0.4,SEK.getRate(),0); //added delta = 0 because I don't accept any error margin
        DKK.setRate(0.5);
        assertEquals(0.5,DKK.getRate(),0);
        EUR.setRate(0.6);
        assertEquals(0.6,EUR.getRate(),0);
    }

    @Test
    public void testGlobalValue() {
        assertEquals(18,(int) SEK.universalValue(120)); //casting to avoid ambiguous method call
        assertEquals(26,(int) DKK.universalValue(130));
        assertEquals(150,(int) EUR.universalValue(100));
    }

    @Test
    public void testValueInThisCurrency() {
        assertEquals(10,(int) SEK.valueInThisCurrency(100,EUR)); //casting to avoid ambiguous method call
        assertEquals(750,(int) EUR.valueInThisCurrency(100,DKK)); // (100 * 1,5)/0,2
    }

}
