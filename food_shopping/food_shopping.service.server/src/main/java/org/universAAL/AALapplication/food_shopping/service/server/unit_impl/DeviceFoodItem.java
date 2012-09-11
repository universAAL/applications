
package org.universAAL.AALapplication.food_shopping.service.server.unit_impl;

import java.util.ArrayList;
import java.util.Iterator;
import org.universAAL.AALapplication.food_shopping.service.server.Activator;
import org.universAAL.middleware.container.utils.LogUtils;

/**
 * @author dimokas
 *
 */

public class DeviceFoodItem {
	String name;
	double quantity;
	String size;
	String company;
	int code;
	String tagID;
	String insDate;
	String expDate;

	public DeviceFoodItem() {}

	public DeviceFoodItem(String name, double quantity) {
		this.name = name;
		this.quantity = quantity;
	}

	public DeviceFoodItem(String name, double quantity, String size, String company, int code) {
		this.name = name;
		this.quantity = quantity;
		this.size = size;
		this.company = company;
		this.code = code;
	}

	public DeviceFoodItem(String itemID, String name, double quantity, String size, String company, String insDate, String expDate) {
		this.tagID = itemID;
		this.name = name;
		this.quantity = quantity;
		this.size = size;
		this.company = company;
		this.insDate = insDate;
		this.expDate = expDate;
	}

	public DeviceFoodItem(String itemID, String name, double quantity, String size, String company, int code) {
		this.tagID = itemID;
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

	public String getTagID() {
		return tagID;
	}

	public void setTagID(String tagID) {
		this.tagID = tagID;
	}
	public String getInsDate() {
		return insDate;
	}

	public void setInsDate(String insDate) {
		this.insDate = insDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

}
