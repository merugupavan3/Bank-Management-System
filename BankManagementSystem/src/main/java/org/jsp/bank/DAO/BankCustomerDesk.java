package org.jsp.bank.DAO;

public class BankCustomerDesk {/*Helper class*/
public static BankDAO customerHelpDesk() {
	BankDAO bankDAO = (BankDAO)new BankDaoImpl();
	return bankDAO;
}
}
