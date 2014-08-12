package org.universAAL.AALapplication.hwo.engine;

import org.universAAL.AALapplication.hwo.model.CoordinateSystem;
import org.universAAL.AALapplication.hwo.model.Point;


// POI class as it is defined in the servlet.

public class POI_UAAL
{
	public String name;
	public Point point;
	
	public POI_UAAL()
	{
		this.name = "none";
		this.point = new Point(0.0,0.0,0.0,CoordinateSystem.WGS84);
		
	}
	public POI_UAAL(String name, Point point)
	{
		this.name = name;
		this.point = point;
		
	}
	public String getName(){
		return name;
	}
	
	public String getCoordinates(){
		String s = Double.toString(point.getX()) +","+Double.toString(point.getY());
		return s;
	}

	
	
}