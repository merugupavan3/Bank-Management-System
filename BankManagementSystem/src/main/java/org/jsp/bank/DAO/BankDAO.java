package org.jsp.bank.DAO;

import org.jsp.bank.model.Bank;

public interface BankDAO {
	void accountCreation();
	void credit();
	void debit();
	void changingThePassword();
	void mobileToMobileTransaction();
	double checkBalance(String accountNumber,String password);
}
