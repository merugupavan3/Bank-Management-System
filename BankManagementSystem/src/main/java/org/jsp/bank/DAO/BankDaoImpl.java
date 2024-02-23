package org.jsp.bank.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
import org.jsp.bank.model.Bank;

public class BankDaoImpl implements BankDAO {/*Implementation class*/
	Scanner sc = new Scanner(System.in);
	String url ="jdbc:mysql://localhost:3306/teca52?user=root&password=12345";
	/**Database validation methods**/
	
/*This method will return true when given account number is already present in the database and it returns
 * false when the given account number is not present in the database */
	public boolean databaseAccountNumberValidation(String accountNumber) {
		String query="select * from bank where accountNumber=?";
		boolean accountStatus=false;
		try {
			Connection connection =DriverManager.getConnection(url);
			PreparedStatement ps =connection.prepareStatement(query);
			ps.setString(1, accountNumber);
			ResultSet resultSet =ps.executeQuery();
			if(resultSet.next()) {
				accountStatus=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountStatus;
	}
	public boolean emailIdValidation(String emailId) {
		boolean emailIdStatus=false;
		String query = "select * from bank where emailId=?";
		try {
			Connection connection =DriverManager.getConnection(url);
			PreparedStatement ps =connection.prepareStatement(query);
			ps.setString(1, emailId);
			ResultSet resultSet =ps.executeQuery();
			if(resultSet.next()) {
				emailIdStatus=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emailIdStatus;
	}
	public boolean databasePasswordValidation(String accountNumber,String password) {
		boolean passwordStatus=false;
		try {
			Connection connection=DriverManager.getConnection(url);
			String query = "select password from bank where accountNumber=? ";
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String databasePassword=resultSet.getString("password");
				passwordStatus=true;
				if (databasePassword.equals(password)) {
					passwordStatus=true;
				}
				else {
					passwordStatus=false;
				}
			}
			else {
				passwordStatus=false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return passwordStatus;
	}
	
	/*This databaseMobileNumberValidation() method returns true when the given mobile number
	 * is present in the database and it returns false when the given mobile number is not
	 * present in the database*/
	public boolean databaseMobileNumberValidation(String mobileNumber) {
		String query = "select * from bank where mobileNumber=?";
		boolean mobileStatus=false;
		try {
			Connection connection =DriverManager.getConnection(url);
			PreparedStatement ps =connection.prepareStatement(query);
			ps.setString(1, mobileNumber);
			ResultSet resultSet =ps.executeQuery();
			if(resultSet.next()) {
				mobileStatus=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mobileStatus;
	}
	

	
	
	/**syntax validation methods**/
	public String mobileNumberValidation(String mobileNumber) {
		boolean mobileNumberStatus=true;
		while(mobileNumberStatus) {
			if(mobileNumber.length()==10) {
				mobileNumberStatus=false;
				if(databaseMobileNumberValidation(mobileNumber)) {
					mobileNumberStatus=false;
				}
				else {
					System.out.println(mobileNumber+" this mobile number is not connected with any account in our bank\n please check and enter the valid mobile number");
					mobileNumber = sc.next();
					mobileNumberStatus=true;
				}
			}
			else {
				mobileNumberStatus=true;
				System.out.println("please enter the valid 10 digit mobile number");
				mobileNumber = sc.next();
			}
		}
		return mobileNumber;
	}
	
	public String accountNumberValidation(String accountNumber) {
		/*This method will valid syntax of the account number and it gives infinite chances to the user, until he
		 * enter the correct 11 digit account number
		 * And also this method after checking the syntax of the account number, it will call databaseAccountNumberValidation()
		 * method, in order to check whether that account number is present in the database or not. so finally designer, no need to call
		 * databaseAccountNumberValidation() method explicitly this method itself calling that method implicitly 
		 */
		boolean accountNumberStatus=true;
		while(accountNumberStatus) {
			if (accountNumber.length()==11) {
				accountNumberStatus=false;
				if (databaseAccountNumberValidation(accountNumber)) {
					accountNumberStatus=false;
				}
				else {
					System.out.println(accountNumber+" this account number is not present in our bank");
					System.out.println("please enter the valid account number");
					accountNumber = sc.next();
					accountNumberStatus=true;
				}
			}
			else {
				System.out.println("please enter the 11 digit account number");
				accountNumber=sc.next();
				accountNumberStatus=true;
			}
		}
		return accountNumber;
	}
	public String passwordValidation(String accountNumber,String password) {
		boolean passwordStatus=true;
		while(passwordStatus) {
			int size =password.length();
			if (size>=4&&size<=12) {
				passwordStatus=false;
				if(databasePasswordValidation(accountNumber,password)) {
					passwordStatus=false;
				}
				else {
					System.out.println("Incorrect password \n please enter the valid password");
					password = sc.next();
					passwordStatus=true;
				}
				
			}
			else {
				System.out.println("please enter the password in the range b/w 4 to 12");
				password = sc.next();
				passwordStatus=true;
			}
		}
		return password;
	}
		
	
	
	
	
	public String randomAccountNumberCreation() {
		/*This Random class is used to generate the random account number for the user*/
		Random random = new Random();
		long accNumber = random.nextLong(100000000000l);
		if (accNumber<10000000000l) {
			accNumber+=10000000000l;
		}
		/*finally some random account number is generated for the user, who is willing to create the
		 * account at this bank. Next we want to confirm that particular random account number is already
		 * given for any account holder ie., that account number is already present in the database or not
		 * For that reason i am developing the method which will check that particular account is already 
		 * present in the database or not. That method name is databaseAccountNumberValidation, this method returns false
		 * when that generated random account number is not repeated and it returns true when that generated
		 * account number is already present in the database */
		String accountNumber=""+accNumber;/*converting integer to string*/
		return accountNumber;
	}

	public String mobileNumberChecking(String mobileNumber) {
		boolean digitStatus=false;
		boolean mobileNumberStatus=true;
		while(mobileNumberStatus) {
			for(int i=0;i<mobileNumber.length();i++) {
				if(Character.isDigit(mobileNumber.charAt(i))) {
					digitStatus=true;
				}
				else {
					digitStatus=false;
					break;
				}
			}
			if(digitStatus) {
				mobileNumberStatus=false;
				if(mobileNumber.length()==10) {
					mobileNumberStatus=false;
				}
				else {
					mobileNumberStatus=true;
					System.out.println("please enter the valid 10 digit mobile number: ");
					mobileNumber=sc.next();
				}
			}
			else {
				mobileNumberStatus=true;
				System.out.println("please enter the mobile number in the form of digits");
				mobileNumber=sc.next();
			}
		}
		return mobileNumber;
	}
	public void accountCreation() {
		System.out.println("Enter your First name: ");
	    String firstName = sc.next();
	    boolean firstNameStatus=true;
	    while(firstNameStatus) {
	    	boolean alphabeticStatus=false;
	    	if(alphabeticStatus) {
	    		
			    
	    	}
	    	else {
	    		for(int i=0;i<firstName.length();i++) {
			    	if(Character.isAlphabetic(firstName.charAt(i))) {
			    		alphabeticStatus=true;
			    		firstNameStatus=false;
			    	}
			    	else {
			    		alphabeticStatus=false;
			    		firstNameStatus=true;
			    		System.out.println("please enter the first name in the form of characters");
			    		firstName=sc.next();
			    		break;
			    	}
			    }
	    	}
	    }
	    
	    System.out.println("Enter your Last name: ");
	    String lastName= sc.next();
	    boolean lastNameStatus=true;
	    while(lastNameStatus) {
	    	boolean alphabeticStatus=false;
	    	if(alphabeticStatus) {
	    		
			    
	    	}
	    	else {
	    		for(int i=0;i<lastName.length();i++) {
			    	if(Character.isAlphabetic(lastName.charAt(i))) {
			    		alphabeticStatus=true;
			    		lastNameStatus=false;
			    	}
			    	else {
			    		alphabeticStatus=false;
			    		lastNameStatus=true;
			    		System.out.println("please enter the last name in the form of characters");
			    		lastName=sc.next();
			    		break;
			    	}
			    }
	    	}
	    }
	    /*Aadhar number should be the 12 digit number*/
	    System.out.println("Enter your Aadhar number: ");
	    String aadharNumber = sc.next();
	    boolean aadharNumberStatus=true;
	    boolean digitStatus=false;
	    while(aadharNumberStatus) {
	    	for(int i=0;i<aadharNumber.length();i++) {
	    		if(Character.isDigit(aadharNumber.charAt(i))) {
	    			digitStatus=true;
	    		}
	    		else {
	    			digitStatus=false;
	    			break;
	    		}
	    	}
	    	if(digitStatus) {
	    		aadharNumberStatus=false;
	    		if(aadharNumber.length()==12) {
		    		aadharNumberStatus=false;
		    	}
		    	else {
		    		aadharNumberStatus=true;
		    		System.out.println("Please enter the 12 digit valid Aadhar number");
		    		aadharNumber=sc.next();
		    	}
	    	}
	    	else {
	    		aadharNumberStatus=true;
	    		System.out.println("please enter the aadhar number in the form of digits");
	    		aadharNumber=sc.next();
	    	}
	    	
	    }
	    System.out.println(aadharNumber);
	    System.out.println("Enter your Address: ");
	    String address = sc.next();
		
	    System.out.println("Enter your Mobile number: ");
	    String mobileNumber = sc.next();
	    mobileNumber=mobileNumberChecking(mobileNumber);
	    /**start doing from here*/
	    
	    
		boolean mobileNumberStatus=true;
		while(mobileNumberStatus) {
			if(databaseMobileNumberValidation(mobileNumber)) {
				System.out.println("An account is already present with this "+mobileNumber+" number \n please give another mobile number");
				mobileNumber = sc.next();
				mobileNumber=mobileNumberChecking(mobileNumber);
			}
			else {
				mobileNumberStatus=false;
			}
		}
		
		System.out.println("Enter your EmailId: ");
	    String emailId = sc.next();
	    boolean emailIdStatus=true;
	    while(emailIdStatus) {
	    	if(emailId.endsWith("@gmail.com")||emailId.endsWith("@yahoo.com")) {
	    		emailIdStatus=false;
	    		if(emailIdValidation(emailId)) {
					System.out.println("Already an account is connected with "+emailId+" this emailid \n please give another email id");
					emailId = sc.next();
					emailIdStatus=true;
				}
				else {
					emailIdStatus=false;
				}
	    	}
	    	else {
	    		System.out.println("please enter the valid email id: ");
	    		emailId=sc.next();
	    		emailIdStatus=true;
	    	}
	    }
		
		/*i am calling the accountNumberCreation() method in order to generate a random account number for the person who
		 * is willing to create a account now*/
		String accountNumber = randomAccountNumberCreation();
		/*Before proceeding with the random generated account number, first we make sure that particular account number
		 * is not repeated for any person in the database. For that reason, i have created a method called databaseAccountNumberValidation method 
		 * which  returns false when that generated random account number is not repeated and it returns true when that generated
		 * account number is already present in the database 
		 * This if block get executed when that randomly generated account number is already given to a particular person
		 * This else block get executed when the random generated account number is not repeated in the database
		 * At that time we need to again call the accountNumberCreation() method in order to another random account number*/
		boolean accountNumberValidationStatus=true;
		while (accountNumberValidationStatus) {
			if (databaseAccountNumberValidation(accountNumber)) {
				accountNumber=randomAccountNumberCreation();
			}
			else {
				accountNumberValidationStatus=false;
			}
		}
		
		System.out.println("An new account is created for you \n Now please set a password for your account");
		String password=sc.next();
		System.out.println("Re-confirm the password: ");
		String repassword = sc.next();
		boolean passwordStatus=true;
		while(passwordStatus) {
			if(password.equals(repassword)) {
				passwordStatus=false;
				System.out.println("passwords are matched");
			}
			else {
				System.out.println("passwords are mismatched \n please enter the re-confirm password as same as previous password ");
				repassword=sc.next();
			}
		}
		String insertingDataOfCustomerIntoDatabase ="insert into bank(userFirstName, userLastName, mobileNumber, emailId, password, amount, address, accountNumber, aadharNumber) values(?,?,?,?,?,?,?,?,?)";
		/*try {
			Connection connection=DriverManager.getConnection(url);/*connection is established*/
			/*PreparedStatement prepareStatement=connection.prepareStatement(insertingDataOfCustomerIntoDatabase);/*platform is created for this query*/
			/*prepareStatement.setString(1, bank.getUserFirstName());
			prepareStatement.setString(2, bank.getUserLastName());
			prepareStatement.setString(3, bank.getMobileNumber());
			prepareStatement.setString(4, bank.getEmailId());
			prepareStatement.setString(5, password);
			prepareStatement.setDouble(6, bank.getAmount());
			prepareStatement.setString(7, bank.getAddress());
			prepareStatement.setString(8, accountNumber);
			prepareStatement.setString(9, bank.getAadharNumber());
			int result=prepareStatement.executeUpdate();
			if (result!=0) {
				System.out.println("Account is created successfully \n your account number is :"+accountNumber);
				System.out.println("Thank you for choosing us.😊😊😊😊");
			}
			else {
				System.out.println("fail to create an account for you");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
	
	
	public void credit() {
		System.out.println("press 1 to credit the money into your account through cash \n press 2 to credit the money from your account to other account");
		int result=sc.nextInt();
		if(result==1) {
			System.out.println("Enter your account number: ");
			String receiverAccountNumber = sc.next();
			receiverAccountNumber = accountNumberValidation(receiverAccountNumber);
			System.out.println("Enter the amount: ");
			double amount = sc.nextDouble();
			String queryToUpdateTheReceiverAccountBalance = "update bank set amount=amount+ ? where accountNumber=?";
			try {
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement ps =connection.prepareStatement(queryToUpdateTheReceiverAccountBalance);
				ps.setDouble(1, amount);
				ps.setString(2, receiverAccountNumber);
				result =ps.executeUpdate();
				if(result!=0) {
					System.out.println("Amount "+amount+" is credited successfully in the account "+receiverAccountNumber);
				}
				else {
					System.out.println("credit failed");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Enter your account number: ");
			String senderAccountNumber = sc.next();
			senderAccountNumber = accountNumberValidation(senderAccountNumber);
			System.out.println("Enter your password: ");
			String password = sc.next();
			password=passwordValidation(senderAccountNumber, password);
			System.out.println("Enter the receiver account number: ");
			String receiverAccountNumber = sc.next();
			receiverAccountNumber =accountNumberValidation(receiverAccountNumber);
			System.out.println("Enter the amount: ");
			double amount = sc.nextDouble();
			double bankAmount = checkBalance(senderAccountNumber, password);
			boolean amountStatus=true;
			while(amountStatus) {
				if(bankAmount>=amount) {
					amountStatus=false;
					String queryToUpdateTheReceiverAccountBalance = "update bank set amount=amount+ ? where accountNumber=?";
					try {
						Connection connection =  DriverManager.getConnection(url);
						PreparedStatement ps =connection.prepareStatement(queryToUpdateTheReceiverAccountBalance);
						ps.setDouble(1, amount);
						ps.setString(2, receiverAccountNumber);
						result =ps.executeUpdate();
						if(result!=0) {
							System.out.println("Amount "+amount+" is credited successfully in the account "+receiverAccountNumber);
						}
						else {
							System.out.println("credit failed");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double remainingSenderAccountBalance=bankAmount-amount;
					String queryToUpdateTheSenderAccountBalance ="update bank set amount=? where accountNumber=? and password=?";
					try {
						Connection connection =DriverManager.getConnection(url);
						PreparedStatement ps =connection.prepareStatement(queryToUpdateTheSenderAccountBalance);
						ps.setDouble(1, remainingSenderAccountBalance);
						ps.setString(2, senderAccountNumber);
						ps.setString(3, password);
						result =ps.executeUpdate();
						if(result!=0) {
							System.out.println("Remaining Balance in your account "+senderAccountNumber +" is: "+remainingSenderAccountBalance);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					System.out.println("Insufficient balance \n press 1 to check your balance \n press 2 to re-enter the amount ");
					int response = sc.nextInt();
					if(response==1) {
						System.out.println("Your Account Balance is: "+checkBalance(senderAccountNumber, password));
					}
					System.out.println("Re-enter the amount: ");
					amount = sc.nextDouble();
				}
			}
		}
		
	}

	public void changingThePassword() {
		System.out.println("Enter your account number: ");
		String accountNumber = sc.next();
		accountNumber = accountNumberValidation(accountNumber);
		System.out.println("Enter the password: ");
		String password = sc.next();
		password =passwordValidation(accountNumber, password);
		System.out.println("Enter your mobile number: ");
		String mobileNumber = sc.next();
		mobileNumber=mobileNumberValidation(mobileNumber);
		String query ="select * from bank where accountNumber=? and mobileNumber=?";/*This query is used to check Whether The Given Mobile Number Is Connected With The Given Account Number*/
		boolean mobileNumberStatus=true;
		while(mobileNumberStatus) {
			try {
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement ps =connection.prepareStatement(query);
				ps.setString(1, accountNumber);
				ps.setString(2, mobileNumber);
				ResultSet resultSet =ps.executeQuery();
				if(resultSet.next()) {
					mobileNumberStatus=false;
				}
				else {
					System.out.println("The given mobile number "+mobileNumber+" is not connected with your account number "+accountNumber);
					System.out.println("please re-enter the mobile number: ");
					mobileNumber=sc.next();
					mobileNumber=mobileNumberValidation(mobileNumber);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sc.next();
		System.out.println("Enter the new password: ");
		String newPassword = sc.nextLine();
		boolean passwordStatus=true;
		while(passwordStatus) {
			if(newPassword.length()>=4&&newPassword.length()<=20) {
				passwordStatus=false;
			}
			else {
				System.out.println("please enter the password in the range b/w 4 to 20 characters");
				System.out.println("Re-enter the new password: ");
				newPassword=sc.nextLine();
				sc.next();
			}
		}
		System.out.println("confirm the new password: ");
		String confirmNewPassword=sc.nextLine();
		sc.next();
		passwordStatus=true;
		while(passwordStatus) {
			if(confirmNewPassword.length()>=4&&confirmNewPassword.length()<=20) {
				passwordStatus=false;
				if(newPassword.equals(confirmNewPassword)) {
					passwordStatus=false;
				}
				else {
					System.out.println("Passwords are mismatched \n please re-enter the confirm password: ");
					confirmNewPassword=sc.nextLine();
					sc.next();
					passwordStatus=true;
				}
			}
			else {
				System.out.println("please enter the confirm password in the range b/w 4 to 20 characters");
				System.out.println("Re-enter the confirm password: ");
				confirmNewPassword=sc.nextLine();
				sc.next();
				passwordStatus=true;
			}
		}
		System.out.println("new password: "+newPassword);
		System.out.println("confirm password: "+confirmNewPassword);
		query ="update bank set password=? where accountNumber=? and mobileNumber=?";
		try {
			Connection connection =DriverManager.getConnection(url);
			PreparedStatement ps =connection.prepareStatement(query);
			ps.setString(1, confirmNewPassword);
			ps.setString(2, accountNumber);
			ps.setString(3, mobileNumber);
			int result=ps.executeUpdate();
			if(result!=0) {
				System.out.println("your account number "+accountNumber+" password is successfully changed");
			}
			else {
				System.out.println("something went wrong");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void mobileToMobileTransaction() {
		System.out.println("Enter your mobile number: ");
		String senderMobileNumber = sc.next();
		senderMobileNumber=mobileNumberValidation(senderMobileNumber);
		String query = "select * from bank where mobileNumber=?";
		try {
			Connection connection =DriverManager.getConnection(url);
			PreparedStatement ps =connection.prepareStatement(query);
			ps.setString(1, senderMobileNumber);
			ResultSet resultSet = ps.executeQuery();
			resultSet.next();
			String senderAccountNumber = resultSet.getString("accountNumber");
			System.out.println("Enter the password: ");
			String password = sc.next();
			password =passwordValidation(senderAccountNumber, password);
			System.out.println("Enter the receiver mobile number: ");
			String receiverMobileNumber = sc.next();
			receiverMobileNumber=mobileNumberValidation(receiverMobileNumber);
			System.out.println("Enter the amount: ");
			double amount = sc.nextDouble();
			double bankAmount = resultSet.getDouble("amount");
			boolean amountStatus=true;
			while(amountStatus) {
				if(bankAmount>=amount) {
					amountStatus=false;
					String queryToCreditTheMoneyIntoTheReceiverAccount="update bank set amount=amount+? where mobileNumber=?";
					ps=connection.prepareStatement(queryToCreditTheMoneyIntoTheReceiverAccount);
					ps.setDouble(1, amount);
					ps.setString(2, receiverMobileNumber);
					int result=ps.executeUpdate();
					if(result!=0) {
						System.out.println("Money transfer is successful from your mobile number "+senderMobileNumber+" to "+receiverMobileNumber);
					}
					else {
						System.out.println("Money transfer is failed due to technical issue");
					}
					double remainingSenderBalance=bankAmount-amount;
					String queryToDebitTheMoneyFromTheSenderAccount ="update bank set amount=? where mobileNumber=?";
					ps =connection.prepareStatement(queryToDebitTheMoneyFromTheSenderAccount);
					ps.setDouble(1, remainingSenderBalance);
					ps.setString(2, senderMobileNumber);
					result =ps.executeUpdate();
					if(result!=0) {
						System.out.println("Remaining Balance in your account is "+remainingSenderBalance);
						System.out.println("Thank you for using this service 😊😊😊😊😊");
					}
					else {
						System.out.println("Money transfer is failed due to technical issue");
					}
					
				}
				else {
					System.out.println("Insufficient balance \n press 1 to check your balance \n press 2 to re-enter the amount ");
					int response = sc.nextInt();
					if(response==1) {
						System.out.println("Your Account Balance is: "+bankAmount);
					}
					System.out.println("Re-enter the amount");
					amount=sc.nextDouble();
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public double checkBalance(String accountNumber,String password) {
		double balance=0.0;
		accountNumber = accountNumberValidation(accountNumber);
		password =passwordValidation(accountNumber, password);
		String query = "select * from bank where accountNumber=? and password=?";
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1, accountNumber);
			ps.setString(2, password);
			ResultSet resultSet = ps.executeQuery();
			resultSet.next();
			balance=resultSet.getDouble("amount");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;
		
	}
	public void debit() {
		System.out.println("Enter the account number: ");
		String accountNumber = sc.next();
		accountNumber = accountNumberValidation(accountNumber);
		System.out.println("Enter the password: ");
		String password = sc.next();
		password=passwordValidation(accountNumber, password);
		boolean amountStatus=true;
		double bankAmount = checkBalance(accountNumber, password);
		while(amountStatus) {
			System.out.println("Enter the amount: ");
			double amount = sc.nextDouble();
			
			if(bankAmount>=amount) {
				amountStatus=false;
				String query ="update bank set amount=? where accountNumber=? and password=?";
				double remainingBalance=bankAmount-amount;
				try {
					Connection connection=DriverManager.getConnection(url);
					PreparedStatement ps =connection.prepareStatement(query);
					ps.setDouble(1, remainingBalance);
					ps.setString(2, accountNumber);
					ps.setString(3, password);
					int result=ps.executeUpdate();
					if(result!=0) {
						System.out.println(amount+" is debited successfully \n Remaining balance is: "+remainingBalance);
					}
					else {
						System.out.println("Debit failed.Try after sometime");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Insufficient balance \n press 1 to check your balance \n press 2 to re-enter the amount ");
				int response = sc.nextInt();
				if(response==1) {
					System.out.println("Your Account Balance is: "+checkBalance(accountNumber, password));
				}
			}
		}
	}

}
