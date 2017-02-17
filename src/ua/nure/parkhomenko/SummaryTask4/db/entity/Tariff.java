package ua.nure.parkhomenko.SummaryTask4.db.entity;

/**
 * Tariff entity.
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class Tariff extends Entity {
	
	private static final long serialVersionUID = 4716395813531234663L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Tariff [name=" + name + "]";
	}		
}
