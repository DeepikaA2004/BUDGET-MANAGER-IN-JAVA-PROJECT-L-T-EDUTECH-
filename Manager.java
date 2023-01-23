
/**
 * @author Varun Iyer
 * @version 2.1
 * @param month,year - to open the file holding the data of the present month and year
 * The class opens the file and does all the file manipulation work*/

package phase2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager {
	
	Scanner input = new Scanner(System.in);
	// CostOperations class defines all methods relating to expense and budget
	CalcOperations cost = new CalcOperations();
	// FileOperations class defines all methods relating to file operations like reading and writing
	FileOperations file = new FileOperations();
	// Password is checked before resetting the budget, PassWord Class object defines the methods needed for this 
	PassWord pass = new PassWord();
	
	public void makeDailyLog() throws IOException
	{
		/**
		 * Method is called by the MenuUI, to get the entry
		 * Asks the user for the date, item, and cost for a single item
		 * calls the enterLog, to make an entry in the Log Book
		 */
		Log log = new Log();
		System.out.print("\nEnter the Date (dd/mm/yyyy): ");
		log.setDate(input.next());
		System.out.print("\nEnter the item: ");
		input.nextLine();
		log.setItem(input.nextLine());
		System.out.print("\nEnter Cost: Rs");
		log.setCost(input.nextInt());
		enterLog(log);
	}
	

	
	private void enterLog(Log log) throws IOException{
		/** 
		 * Method appends the data that is given to it by the MEMClass
		 * The set budget set in the "Budget.txt" file, is checked, and if the budget exceeds, a waring message is flashed
		 * The budget and the expense is updated
		 */
		int budget = 0, expense = 0;
		String budgetBookData = file.getBudgetData(log.getDate());
		// get the budget set for the month from the "Budget.txt" file
		if(budgetBookData == null){
			System.out.print("\nThe budget has not been set yet, please set the budget: ");
			System.out.print("\nThe budget for the month is Rs. ");
			budget = input.nextInt();
			file.updateLogBook(budget, 0, log.getDate(), "nil", 0);
		}
		
		else{
		budget = cost.getBudget(budgetBookData);
		// get the expense incurred till date from the "Budget.txt" file
		expense = cost.getExpense(budgetBookData);
		// Checking if the expense has exceeded the budget set, if yes, displaying the message
		}
		System.out.print("\nLog added Successfully!");
		if(cost.budgetCheck(budget, expense, log.getCost()))
			System.out.println("\n!!!Your monthly budget has exceeded, Please take care !!!");
		
		//Appending the file with new data
		file.updateLogBook(budget, (expense+log.getCost()), log.getDate(), log.getItem(), log.getCost());
		}
	
		public void displayMonthExpense() throws IOException {
			/**
			 * Method to display the expense done till date.
			 */
			System.out.print("Enter month and year (MM/YYYY)");
			String date = input.next();
			//setting date in the dd/mm/yyyy fromat so that getBudgetData can be exploited for the purpose
			date = "00/".concat(date);
			String budgetdata = file.getBudgetData(date);
			// get the expense incurred till date from the "Budget.txt" file
			int expense = cost.getExpense(budgetdata);
			int budget = cost.getBudget(budgetdata);
			System.out.print("\nThe expense done till date is: Rs." + expense);
			if(budget > expense)
				System.out.print("\nThe amount that can yet be spent is: Rs." + (budget - expense));
			else 
				System.out.print("\nThe amount exceeding your budget is: Rs." + (expense - budget));
		}	
		
		public void getDayExpenseDetails() throws IOException{
			/**
			 * Method to get the date on which the expense is to be shown to the user
			 * Calls the getDetails method, to get the details from the Log Book
			 */
			System.out.print("\nEnter the Date (dd/mm/yyyy): ");
			String date = input.next();
			getDetails(date);
		}
		private void getDetails(String date) throws IOException {
		/**
		 * Method accepts the date from the user, and Compares the date with all the dates in the file
		 * @return the details if the date is present, or an exception if the date is not present 
		 */
			ArrayList<Log> list1 = file.getLog(date);
			int n = list1.size();
			if(n == 0)
				System.out.print("\nNo entry has been made for the date entered! Please Check the date");
			else{
				System.out.println("\nThe details of the expense done on " + date + " :");
				for(int i = 0; i < n; i++){
					Log log = list1.get(i);
					System.out.println((i+1) + " : " + "\nExpense Item: " + log.getItem() + "\nExpenditure: Rs." + log.getCost());
					}
				}
			}
		
		public void setBudget() throws IOException {
			/**
			 * Method to get the budget from the user, and re write the "BudgetFile.txt"
			 * Warns the user that setting budget would set the expense to zero
			 */
			if(pass.checkPassWord()){
			System.out.print("\nResetting the budget may create error in the calculations\nDo you still want to continue? (y/n): ");
			char ch = input.next().charAt(0);
			if(ch == 'y' || ch == 'Y'){
				// if the condition is true, the budget is taken from the user, and updated in the "Budget.txt"
				System.out.print("\nEnter the month and the year(MM/YYYY)");
				String date = input.next();
				System.out.print("\nEnter the budget: Rs.");
				int budget = input.nextInt();
				file.resetBudget(date,budget);
				System.out.print("\nThe Budget has been Reset Successfully!");
			}
			ch = 'n';
			}
			else
				System.out.print("The entered password is incorrect!");
			
		}

		public void getMonthExpenseDetails() throws NumberFormatException, IOException {
			/**
			 * Method is used by the MenuUI
			 * Gives the entries made for a whole month to the user*/
			System.out.print("\nEnter the month and the year (MM/YYYY): ");
			String date = input.next();
			ArrayList<Log> list1 = file.getMonthLog(date);
			int n = list1.size();
			if(n == 0)
				System.out.print("\nNo entry has been made for the date entered! Please Check the date");
			else{
				System.out.println("\nThe details of the expense done on " + date + " :");
				for(int i = 1; i < n; i++){
					Log log = list1.get(i);
					System.out.println((i) + " : " + "\nDate: " + log.getDate() + " : " + "\nExpense Item: " + log.getItem() + "\nExpenditure: Rs." + log.getCost());
					}
				}
			}



		public void deleteMonthLog() {
			/**
			 * Method to delete a whole log 
			 * The date is taken from the user
			 * */
			
			System.out.print("Are you sure that you want to delete a whole month's log forever? (Y/N): ");
			char choice = input.next().charAt(0);
			if(choice == 'y' || choice == 'Y'){
				System.out.print("\nEnter the month and the year (MM/YYYY): ");
				String date = input.next();
				file.deleteLog(date);
			}
		}
		
}
