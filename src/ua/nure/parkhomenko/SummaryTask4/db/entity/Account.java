package ua.nure.parkhomenko.SummaryTask4.db.entity;

import ua.nure.parkhomenko.SummaryTask4.db.Status;

/**
 * Account entity. Account can be blocked if money is less than 0
 * or it can be blocked by Admin.
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class Account extends Entity{

	private static final long serialVersionUID = 6429765654950924518L;
	
	private User user;
	
	private double money;
	
	private Status isBlocked;
	
	private Status isBlockedByAdmin;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Status getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Status isBlocked) {
		this.isBlocked = isBlocked;
	}
	
	public Status getIsBlockedByAdmin() {
		return isBlockedByAdmin;
	}
	
	public void setIsBlockedByAdmin(Status isBlockedByAdmin) {
		this.isBlockedByAdmin = isBlockedByAdmin;
	}

	@Override
	public String toString() {
		return "Account [user=" + user + ", money=" + money + ", isBlocked=" + isBlocked + ", isBlockedByAdmin="
				+ isBlockedByAdmin + "]";
	}
}
