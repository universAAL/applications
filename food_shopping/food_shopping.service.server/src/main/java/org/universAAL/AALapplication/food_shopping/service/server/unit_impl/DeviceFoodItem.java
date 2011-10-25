
package org.universAAL.AALapplication.food_shopping.service.server.unit_impl;

import java.util.ArrayList;
import java.util.Iterator;
import org.universAAL.middleware.util.LogUtils;
import org.universAAL.AALapplication.food_shopping.service.server.Activator;


/**
 * @author dimokas
 *
 */

public class DeviceFoodItem {
	String name;
	int quantity;

	public DeviceFoodItem() {}

	public DeviceFoodItem(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
