package ua.nure.parkhomenko.SummaryTask4.db.entity;

import java.util.Date;

/**
 * Contract entity.
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class Contract extends Entity {
	
	private static final long serialVersionUID = 1692970007951320057L;
	
	private User user;

	private ServicesTariffs servicesTariffs;
	
	private Date date;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ServicesTariffs getServicesTariffs() {
		return servicesTariffs;
	}

	public void setServicesTariffs(ServicesTariffs servicesTariffs) {
		this.servicesTariffs = servicesTariffs;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Contract [user=" + user + ", servicesTariffs=" + servicesTariffs + ", date=" + date + "]";
	}	
}
