package ua.nure.parkhomenko.SummaryTask4.db.entity;

import java.io.Serializable;

/**
 * Basic common parent for all entities. Provides id field and get/set methods
 * for him.
 *
 * @author Tetiana Parkhomenko
 *
 */
public abstract class Entity implements Serializable {

	private static final long serialVersionUID = 8466257860808346236L;

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}