package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("Nordea", Nordea.getName());
        assertEquals("DanskeBank",DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK,Nordea.getCurrency());
        assertEquals(DKK,DanskeBank.getCurrency());
	}

	@Test(expected = AccountExistsException.class) //pass if this exception is thrown
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
        Nordea.openAccount("JaneDoe");
        Nordea.openAccount("JaneDoe"); //adding the same value in order to trigger the exception
	}

	@Test(expected = AccountDoesNotExistException.class)
	public void testDeposit() throws AccountDoesNotExistException, AccountExistsException {
        assertEquals(0,(int) SweBank.getBalance("Bob"));
        SweBank.deposit("Bob",new Money(100,SEK)); //check if balance changed
        assertEquals(100,(int) SweBank.getBalance("Bob"));
        DanskeBank.deposit("BYTStudent",new Money(600,DKK));//should throw an exception
	}

	@Test(expected = AccountDoesNotExistException.class) //spotted error in withdraw method
	public void testWithdraw() throws AccountDoesNotExistException {
        assertEquals(0,(int) SweBank.getBalance("Bob"));
        SweBank.withdraw("Bob",new Money(100,SEK)); //check if balance changed
        assertEquals(-100,(int) SweBank.getBalance("Bob"));
        DanskeBank.deposit("BYTStudent",new Money(600,DKK));//should throw an exception
	}

	@Test(expected = AccountDoesNotExistException.class)
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(0,(int) SweBank.getBalance("Ulrika"));
        SweBank.getBalance("AnotherBYTStudent"); //checking balance of not existing person
	}

	@Test (expected =  AccountDoesNotExistException.class)
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika",new Money(100,SEK));
        assertEquals(100, (int) SweBank.getBalance("Ulrika"));
        SweBank.transfer("Ulrika",DanskeBank,"Gertrud",new Money(10,SEK));
        assertEquals(5, (int) DanskeBank.getBalance("Gertrud"));
		SweBank.transfer("IDontExist","Same",new Money(1000000,SEK)); //throw an exception
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.deposit("Bob",new Money(100,SEK));
		SweBank.addTimedPayment("Bob","Test",0,0,new Money(10,SEK),DanskeBank,"Gertrud");
		for (int i = 0; i < 5; i++) {
			SweBank.tick();
		}
		assertEquals(25,(int) DanskeBank.getBalance("Gertrud"));
	}
}
