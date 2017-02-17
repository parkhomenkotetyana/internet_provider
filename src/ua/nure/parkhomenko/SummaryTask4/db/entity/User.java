package ua.nure.parkhomenko.SummaryTask4.db.entity;

import java.util.Date;

/**
 * User entity.
 * 
 * @author Tetiana Parkhomenko
 * 
 */
public class User extends Entity {

	private static final long serialVersionUID = -6889036256149495388L;

	private String fullName;

	private Date dateOfBirth;

	private String address;

	private String passport;

	private String login;

	private String password;

	private int roleId;

	public User(String fullName, Date dateOfBirth, String address, String passport, String login, String password, int roleId) {
		super();
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.passport = passport;
		this.login = login;
		this.password = password;
		this.roleId = roleId;
	}

	public User() {}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}	

	@Override
	public String toString() {
		return "User [fullName=" + fullName + ", dateOfBirth=" + dateOfBirth + ", address=" + address + ", passport="
				+ passport + ", login=" + login + ", password=" + password + ", roleId=" + roleId + "]";
	}
}