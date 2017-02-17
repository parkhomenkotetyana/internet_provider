package ua.nure.parkhomenko.SummaryTask4.db.entity;

/**
 * ServicesTariffs entity.
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class ServicesTariffs extends Entity{

	private static final long serialVersionUID = -2473393790923676514L;
	
	private Service service;
	
	private Tariff tariff;
	
	private double price;
	
	private String description;

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Tariff getTariff() {
		return tariff;
	}

	public void setTariff(Tariff tariff) {
		this.tariff = tariff;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "ServicesTariffs [service=" + service + ", tariff=" + tariff + ", price=" + price + ", description=" + description
				+ "]";
	}	
}
