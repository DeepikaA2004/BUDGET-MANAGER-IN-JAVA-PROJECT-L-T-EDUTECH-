package phase1;

import java.util.StringTokenizer;

/**
 * @author Varun Iyer
 * @version 1.1
 * @param budget - stores the budget of the month
 * @param expense - stores the expenses done till date, in that particular month
 * @param dat - object of class Data(), in package phase1
 */
public class CostOperations {

	public boolean budgetCheck(int budget, int expense, int cost) {
		if(budget < (expense + cost))
			return true;
		return false;
	}

	public int getExpense(String temp) {
		StringTokenizer st = new StringTokenizer(temp, " "); 
		int[] a = {0, 0};
		int i = 0; 
		while(st.hasMoreTokens())
		{
			a[i] = Integer.parseInt(st.nextToken());
			i++;
		}	
		
		return a[1];
	}
	public int getBudget(String temp) {
		StringTokenizer st = new StringTokenizer(temp, " "); 
		int[] a = {0, 0};
		int i = 0; 
		while(st.hasMoreTokens())
		{
			a[i] = Integer.parseInt(st.nextToken());
			i++;
		}	
		
		return a[0];
	}

}
