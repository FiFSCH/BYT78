package b_Money;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, EUR; //deleted NOK as it was not initialized in the setUp method
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(10000, (int) SEK100.getAmount()); //casting to avoid ambiguous method call
        assertEquals(1000, (int) EUR10.getAmount());
        assertEquals(20000, (int) SEK200.getAmount());
        assertEquals(2000, (int) EUR20.getAmount());
        assertEquals(0, (int) SEK0.getAmount());
        assertEquals(0, (int) EUR0.getAmount());
        assertEquals(-10000, (int) SEKn100.getAmount());
	}

	@Test
	public void testGetCurrency() {
        assertEquals(SEK, SEK100.getCurrency());
        assertEquals(EUR,  EUR10.getCurrency());
        assertEquals(SEK,  SEK200.getCurrency());
        assertEquals(EUR,  EUR20.getCurrency());
        assertEquals(SEK,  SEK0.getCurrency());
        assertEquals(EUR,  EUR0.getCurrency());
        assertEquals(SEK, SEKn100.getCurrency());
	}

	@Test
	public void testToString() { //Returns the amount of the money in the string form "(amount) (currencyName)", e.g. "10.5 SEK".
		assertEquals("100.0 SEK",SEK100.toString());
        assertEquals("10.0 EUR",EUR10.toString());
        assertEquals("200.0 SEK",SEK200.toString());
        assertEquals("20.0 EUR",EUR20.toString());
        assertEquals("0.0 SEK",SEK0.toString());
        assertEquals("-100.0 SEK",SEKn100.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(1500, (int) SEK100.universalValue());
		assertEquals(0, (int) SEK0.universalValue());
		assertEquals(3000, (int) EUR20.universalValue());
		assertEquals(-1500, (int) SEKn100.universalValue());
	}

	@Test
	public void testEqualsMoney() { //see if the value of this money is equal to the value of another Money of some other Currency.
		assertTrue(SEK100.equals(EUR10)); //Equal
		assertFalse(EUR20.equals(SEK100));// Not equal
	}

	@Test
	public void testAdd() { //Adds Money to other Money, regardless of the Currency of this Money.
		assertEquals(SEK100.add(EUR10).getAmount(), new Money(20000, SEK).getAmount());
		assertEquals(EUR10.add(SEK0).getAmount(), EUR10.getAmount()); // + 0
	}

	@Test
	public void testSub() { //subtracts money from other Money, regardless of the Currency of this Money.
		assertEquals(SEK100.sub(EUR10).getAmount(), SEK0.getAmount());
		assertEquals(SEKn100.sub(EUR20).getAmount(), new Money(-30000, SEK).getAmount());
	}

	@Test
	public void testIsZero() {
		assertTrue(EUR0.isZero()); //is zero
		assertTrue(SEK0.isZero()); //is zero
		assertFalse(SEK100.isZero()); //this and below are not zero
		assertFalse(SEKn100.isZero());
		assertFalse(EUR20.isZero());
		assertFalse(EUR10.isZero());
	}

	@Test
	public void testNegate() { //negates the value of money
		assertTrue(SEK100.negate().equals(SEKn100));
		assertTrue(EUR10.negate().equals(new Money(-1000, EUR)));
	}

	@Test
	public void testCompareTo() { //0 if values are equal; 1 if first is worth more than the second;
		assertEquals(0,SEK100.compareTo(EUR10)); //-1 if first is worth less than the second
		assertEquals(-1,SEKn100.compareTo(SEK0));
		assertEquals(1, EUR20.compareTo(EUR10));
	}
}
