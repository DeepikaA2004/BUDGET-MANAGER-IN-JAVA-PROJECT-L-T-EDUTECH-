package phase2;

/**
 * @author Varun Iyer
 * @version 2.1
 * PassWord class deals with methods to keep password
 * Accesses methods from the FileOperations Class to read and write the password from the file password.txt
 * */

import java.io.IOException;
import java.util.Scanner;

public class PassWord {
	
	FileOperations file = new FileOperations();
	Scanner input = new Scanner(System.in);
	
	public boolean checkPassWord() throws IOException{
		/**
		 * public method used by the MenuUI to check the password with the one in the file
		 * */
		System.out.print("\nEnter the password: ");
		String pass = input.next();
		checkUntilPasswordIsCorrect(pass);
		//the checkUntilPasswordIsCorrect() method checks the password until it is correct and the else condition need not be checked
		//Thus, true can be returned, without checking any other condition
		return true;
			
	}
	
	public void changePassWord() throws IOException{
		/**
		 * public method used by the MenuUI to set the new password
		 * the method writes the new password in the password.txt using file.setPassWord()
		 * */
		System.out.print("\nEnter the old Password: ");
		String oldPass = input.next();
		
		if(checkUntilPasswordIsCorrect(oldPass)){
		System.out.print("\nEnter the new Password: ");
		String newPass = input.next();
		file.setPassWord(newPass);
		System.out.print("\nThe password has been changed successfully!");
		}
	}

	private String getPassWord() throws IOException{
		/**
		 * private method accesses the file.getPassWord() to get the password from the file password.txt
		 */
		String pass = file.getPassWord();
		return pass;
	}

	private boolean checkUntilPasswordIsCorrect(String oldPass) throws IOException {
		/**
		 * A private method to loop until the correct password is entered
		 * Used by both checkPassword and changePassword
		 * */
		// While loop will execute till the time the password entered is the old password
		while(oldPass.equals(getPassWord()) == false){
			System.out.print("The entered password is incorrect! \n\nRe-enter the password: ");
			oldPass = input.next();
			// if the password entered is equal to the old password from the file, then the if statement will break the loop.
			if(oldPass.equals(getPassWord()))
				break;
			}
		return true;	
			}
}
