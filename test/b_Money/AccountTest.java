package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK; //removed DDK as it is not initialized in setUp()
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK)); //error was here
	}

	@Test
	public void testAddRemoveTimedPayment() { //add new timedPayment and check if it exists
		Money testMoney = new Money(10,SEK);
		testAccount.addTimedPayment("1",1,1,testMoney,SweBank,"Alice");
		assertTrue(testAccount.timedPaymentExists("1"));
		testAccount.removeTimedPayment("1"); //then remove it and check again
		assertFalse(testAccount.timedPaymentExists("1"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException { //check if after payments balance is correct
		testAccount.addTimedPayment("1", 0, 0, new Money(1000, SEK), SweBank, "Alice");
		for (int i = 0; i < 5; i++) {
			testAccount.tick(); //error in method tick
		}
		assertEquals(9995000, (int) testAccount.getBalance().getAmount());
	}

	@Test
	public void testWithdraw() { //changed name to testWithdraw
		Money testMoney = new Money(1000, SEK);
		testAccount.withdraw(testMoney);
		assertEquals(9999000,(int) testAccount.getBalance().getAmount());
	}					//init value - 1000

	@Test
	public void testGetBalance() {
		Money testMoney = new Money(10000000, SEK);
		assertTrue(testMoney.equals(testAccount.getBalance()));
	}
}
