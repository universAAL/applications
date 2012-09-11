
package org.universAAL.AALapplication.food_shopping.service.server.unit_impl;

import java.util.ArrayList;
import java.util.Iterator;
import org.universAAL.AALapplication.food_shopping.service.server.Activator;
import org.universAAL.middleware.container.utils.LogUtils;

/**
 * @author dimokas
 *
 */

public class CodeFoodItem {
	int code;
	String name;
	String size;
	String company;
	double quantity;

	public CodeFoodItem() {}

	public CodeFoodItem(String name, double quantity) {
		this.name = name;
		this.quantity = quantity;
	}

	public CodeFoodItem(int code, String name, String size, String company) {
		this.name = name;
		this.quantity = quantity;
		this.size = size;
		this.company = company;
		this.code = code;
	}

	public CodeFoodItem(int code, String name, String size, String company, double quantity) {
		this.name = name;
		this.quantity = quantity;
		this.size = size;
		this.company = company;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
