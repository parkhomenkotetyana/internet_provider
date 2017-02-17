package ua.nure.parkhomenko.SummaryTask4.db.entity;

/**
 * Service entity.
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class Service extends Entity {
	
	private static final long serialVersionUID = 4716395418531234663L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Service [name=" + name + "]";
	}	
}
