package phase2;
/**
 * @author Varun Iyer
 * @version 2.1
 * @param date - accepts the date of a current entry
 * @param item - accepts the current item name
 * @param cost - accepts the expense done on the current day
 */
public class Log {

	private String date;
	private String item;
	private int cost;
	
	public void setDate(String date) {
		this.date = date;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getDate() {
		return date;
	}
	
	public String getItem() {
		return item;
	}
	
	public int getCost() {
		return cost;
	}

	
	
	
}
