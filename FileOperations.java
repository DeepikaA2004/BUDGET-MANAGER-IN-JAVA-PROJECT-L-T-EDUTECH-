package phase2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author Varun Iyer
 * @version 2.1
 * Deals with all the File related operation
 */

/**
 * A new method called open file, called inside the updateLogBook method, which will open the file of a particular date and month. (done)
 * A new Log and Budget for each month should be created{
 * >>Use a single file for storing both log and (budget + expense)
 * >>if the file is empty,prompt for setting the budget
 * >>Let the first value be null, null, null, budget, 0
 * >>read the last line of the file for budget and expense details
 * >> while updating the budget, update the last line of the file ( copy the details, delete the field, re write it)
 *  } 
 * Changes in the reading pattern are to be made
 * resetLogBook method will not work, and the month and year has to be taken while attempting to reset the budget and the log 
 * */
public class FileOperations {
	
	File logBookFile;
	File passWord = new File("password.txt");
	Scanner input = new Scanner(System.in);
	
	public void updateLogBook(int budget, int expense, String date, String item, int cost) throws IOException {
		/**
		 * Method used by enterData, to update "LogBook.txt" with the input given by the user
		 */
		// BufferedWriter , to append the data in the "Data.txt" file
		logBookFile = new File("logBook" + getMonthYearName(date) + ".txt");
		FileWriter writer = new FileWriter(logBookFile,true); 
		BufferedWriter br = new BufferedWriter(writer);
		if(item == "nil")
			date = "nil";		//when the first entry for the budget has to be made, the date is registered as nil.
		br.append(budget + "\t" + expense + "\t" + date + "\t" + item + "\t" + cost );
		br.newLine();
		br.close();
	}
	
	public String getBudgetData(String date) throws IOException{
		/**
		 * Method is used by enterLog in Manager
		 * The method prompts the user to enter new budget, if the budget is not entered
		 * Else, it goes to the end of the file and checks and returns the budget and the expense
		 * */
		logBookFile = new File("logBook" + getMonthYearName(date) + ".txt");
		if(!logBookFile.exists()){
			logBookFile.createNewFile();
		}
		FileReader read = new FileReader(logBookFile);
		BufferedReader rd = new BufferedReader(read);
		String budgetData = null;
		String line, last = null;
		if(rd.readLine() == null){
			budgetData = null;
		}
		else{
			// the while loop will give the whole last line
			while ((line = rd.readLine()) != null) {
				last = line;
		    }
			//Extract the last line with a String Tokenizer
			String[] arr = new String[5];
			int i = 0;
			StringTokenizer st = new StringTokenizer(last, "\t");
			while(st.hasMoreTokens()){
				arr[i] = st.nextToken();
				i++;
			}
			budgetData = arr[0].toString().concat(" " + arr[1].toString());
		}	
		rd.close();
		return budgetData;
		
	}
	public ArrayList<Log> getLog( String date) throws IOException
	{
		/**
		 * Method to check if the given date exists in the file
		 * if the date exists, then the details are wrapped in Data object, and returned to the manager
		 * else, null is returned
		 * 
		 * */
	
		logBookFile = new File("logBook" + getMonthYearName(date) + ".txt");
		FileReader read = new FileReader(logBookFile);
		BufferedReader rd = new BufferedReader(read);
		String temp = new String();
		// The data object is added to an array list
		ArrayList<Log> list1 = new ArrayList<Log>();
		while((temp = rd.readLine()) != null ){
			Log log = new Log();
			StringTokenizer st = new StringTokenizer(temp, "\t");
			String[] str = new String[5];
			int i = 0;
			while(st.hasMoreTokens()){
				// Store all the tokens of a particular line in str[]
				str[i] = st.nextToken();
				i++;
			}
			
			if(str[2].toString().equals(date))
			{
				// in case the date give by the user matches with the date in the file, the details are wrapped in log, and returned to manager
				log.setDate(str[2]);
				log.setItem(str[3]);
				log.setCost(Integer.parseInt(str[4]));
				list1.add(log);
			}
		}
		rd.close();
		
		return list1;	
	}

	public void resetBudget(String date, int budget) throws IOException{
		/**
		 * Method gets the date and the budget from the class Manager
		 * Resets the budget in the last line of the file, so that the new budget will be read the next time 
		 * */

		String last = "";
		String line = "";
		logBookFile = new File("logBook" + getMonthYearName(date) + ".txt");
		
		FileReader read = new FileReader(logBookFile);
		BufferedReader rd = new BufferedReader(read);
		
		while ((line = rd.readLine()) != null) {
			last = line;
			}
		StringTokenizer st = new StringTokenizer(last, "\t");
		int i = 0;
		String[] arr = new String[5];
		while(st.hasMoreTokens()){
			arr[i] = st.nextToken();
			i++;
		}
		rd.close();
		
		//Deleting the last line of the file, and add it again with the previous contents but new budget
		RandomAccessFile f = new RandomAccessFile("logBook" + getMonthYearName(date) + ".txt", "rw");
		long length = f.length() - 1;
		byte b;
		do {  
		  // Start reading the bytes from the 2nd last byte, and read till the next linefeed character occours. Once this occours, truncate the file	
		  length -= 1;
		  f.seek(length);
		  b = f.readByte();
		} while(b != 10 && length > 0);
		if (length == 0) { 
		f.setLength(length);
		} else {
		f.setLength(length + 1);
		}
		f.close();
		updateLogBook(budget, Integer.parseInt(arr[1].toString()), arr[2], arr[3], Integer.parseInt(arr[4].toString()));
		
	}
	
	public ArrayList<Log> getMonthLog(String date) throws IOException {
		
		logBookFile = new File("logBook" + getMonthYearName(date) + ".txt");
		FileReader read = new FileReader(logBookFile);
		BufferedReader rd = new BufferedReader(read);
		String temp = new String();
		// The data object is added to an array list
		ArrayList<Log> list1 = new ArrayList<Log>();

		while((temp = rd.readLine()) != null ){
			Log log = new Log();
			StringTokenizer st = new StringTokenizer(temp, "\t");
			String[] str = new String[5];
			int i = 0;
			while(st.hasMoreTokens()){
				// Store all the tokens of a particular line in str[]
				str[i] = st.nextToken();
				i++;
			}
			
			log.setDate(str[2]);
			log.setItem(str[3]);
			log.setCost(Integer.parseInt(str[4]));
			list1.add(log);
		}
		
		rd.close();
		return list1;
	}
	

	public void deleteLog(String date) {
		/**Method to delete a whole log Book
		 * Date is given by the manager
		 * */
		
		logBookFile = new File("logBook" + getMonthYearName(date) + ".txt");
		boolean bool = true;
		
		if(logBookFile.exists())
			bool  = logBookFile.delete();
		
		if(bool == true){
			System.out.print("\nFile Deleted Successfully !");
		}
		else
			System.out.print("\nFile for the particular month does not exist !");
		
	}
	
	private String getMonthYearName(String date) {
		// method generates the month and year in the form of a string, so the particular log and budget can be maintained for a particular month, given the date 
		
		String name = "";
		if(date.length() == 7)
			date = "00/".concat(date);
		StringTokenizer st = new StringTokenizer(date, "/");
		int i = 0;
		String[] arr = new String[3];
		while(st.hasMoreTokens()){
			arr[i] = st.nextToken();
			i++;
		}
		if(date.length() == 10)
			name = arr[1].toString().concat(arr[2].toString());
		else if(date.length() == 8)
			name = arr[0].toString().concat(arr[1].toString());
		return name;
	}

	public String getPassWord() throws IOException {
		/**
		 * Method is used by the PassWord class
		 * Reads the password from the password.txt and returns it to the PassWord class
		 * */
		//In case the password.txt is empty, the it is assumed that the user is using the product for the first time and he is prompted to check the password.txt
		if(passWord.length() == 0)
			System.out.print("\nPlease check the password.txt file for errors!");
		FileReader read = new FileReader(this.passWord);
		BufferedReader rd = new BufferedReader(read);
		// read the password from the file password.txt and return the password
		String pass = rd.readLine().toString().trim();
		rd.close();
		return pass;
	}

	public void setPassWord(String passWord) throws IOException {
		/**
		 * Method is used by the PassWord class
		 * deletes the old password from the password.txt and writes the new password in it
		 * */
		RandomAccessFile f = new RandomAccessFile("password.txt", "rw");
		long length = f.length() - 1;
		byte b;
		do {  
		  // Start reading the bytes from the 2nd last byte, and read till the next linefeed character occours. Once this occours, truncate the file	
		  length -= 1;
		  f.seek(length);
		  b = f.readByte();
		} while(b != 10 && length > 0);
		if (length == 0) { 
		f.setLength(length);
		} else {
		f.setLength(length + 1);
		}
		f.close();
		//writing the new password in the password.txt using the buffered writer
		FileWriter writer = new FileWriter(this.passWord,true); 
		BufferedWriter br = new BufferedWriter(writer);
		br.write(passWord);
		br.close();
		System.out.print("\nYour password has been changed!");
	}

	
}
