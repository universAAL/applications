package org.universAAL.AALapplication.hwo;

// POI class as it is defined in the servlet.

import org.universAAL.ontology.location.position.CoordinateSystem;
import org.universAAL.ontology.location.position.Point;



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