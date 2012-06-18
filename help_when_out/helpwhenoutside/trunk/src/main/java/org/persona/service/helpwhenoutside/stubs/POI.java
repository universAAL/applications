package org.persona.service.helpwhenoutside.stubs;

import org.universAAL.ontology.location.position.CoordinateSystem;
import org.universAAL.ontology.location.position.Point;



public class POI
{
	public String name;
	public Point point;
	
	public POI()
	{
		this.name = "none";
		this.point = new Point(0.0,0.0,CoordinateSystem.WGS84);
		
	}
	public POI(String name, Point point)
	{
		this.name = name;
		this.point = point;
		
	}
	public boolean equals(Object otherPOIObject) {
		
		POI otherPOI = null; 
		
		try {
			otherPOI = (POI) otherPOIObject;
		} catch (ClassCastException e)
		{
			// not a POI instance
			return false;
		}
		
		double myCoord[] = point.get2DCoordinates();
		double otherCoord[] = otherPOI.point.get2DCoordinates();
		
		if (myCoord[0] != otherCoord[0] || myCoord[1] != otherCoord[1] ||  ! otherPOI.name.equalsIgnoreCase(name))
			return false;
		
		return true;
	}
	
	
}