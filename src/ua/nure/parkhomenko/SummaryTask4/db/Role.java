package ua.nure.parkhomenko.SummaryTask4.db;

import ua.nure.parkhomenko.SummaryTask4.db.entity.User;

/**
 * Role entity.
 * 
 * @author D.Kolesnikov
 * 
 */
public enum Role {
	ADMIN, SUBSCRIBER;
	
	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}	
}