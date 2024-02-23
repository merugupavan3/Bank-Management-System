package org.jsp.bank.DAO;

import java.util.Scanner;

import org.jsp.bank.model.Bank;

public class APP {
public static void main(String[] args) {
	BankDAO bankDAO = BankCustomerDesk.customerHelpDesk();
	System.out.println("Enter \n 1.To create an account in our bank \n 2.for credit \n 3.for debit \n 4.for changing the password of your account\n 5.for mobile to mobile transaction \n 6.for check balance " );
	Scanner sc = new Scanner(System.in);
	int response=0;
	try {
		response = sc.nextInt();
	}
	catch(Exception e) {
		System.out.println("please enter the numbers to perform the above operations");
	}
	
	switch (response) {
	case 1:bankDAO.accountCreation();
			break;
	case 2:bankDAO.credit();
			break;
	case 3:bankDAO.debit();
		   break;
	case 4:bankDAO.changingThePassword();
			break;
		   
	case 5:bankDAO.mobileToMobileTransaction();
			break;
	case 6:System.out.println("Enter the account number");
			String accountNumber = sc.next();
			System.out.println("Enter the password");
			String password = sc.next();
			System.out.println("Your balance is: "+bankDAO.checkBalance(accountNumber, password));
			break;
	      
	default:
		break;
	}
}
}
