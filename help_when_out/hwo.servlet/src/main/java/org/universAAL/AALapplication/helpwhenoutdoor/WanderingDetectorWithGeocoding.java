package org.universAAL.AALapplication.helpwhenoutdoor;

import java.util.Vector;

import org.universAAL.AALapplication.helpwhenoutdoor.common.Geocoding;

public class WanderingDetectorWithGeocoding {

	protected class StreetAccess {
		String street;
		int minNumber;
		int maxNumber;
		long inTimestamp;
		long outTimestamp;
	
		protected StreetAccess (){
			street=null;
			minNumber=0;
			maxNumber=0;
			inTimestamp=-1;
		}
	}

	public Vector streetAccesses;
	
	public WanderingDetectorWithGeocoding(){
		streetAccesses= new Vector();
	}
	
	public boolean addAddress(double [] position, long timestamp){
		if (position==null || position.length<2)
			return false;
		String [] streetAndNumber= Geocoding.getStreetAndNumber(position);
		if (streetAndNumber==null)
			return false;
		int [] minMaxNumber;
		if (streetAndNumber.length==2)
			minMaxNumber= parseNumber(streetAndNumber[1]);
		else
			minMaxNumber = new int []{0,0};
		if (streetAccesses.size()==0)
		{
			StreetAccess currentSA= new StreetAccess();
			currentSA.street=streetAndNumber[0];
			currentSA.inTimestamp=timestamp;
			currentSA.minNumber=minMaxNumber[0];
			currentSA.maxNumber=minMaxNumber[1];
			streetAccesses.add(currentSA);
			return true;
		}
		
		StreetAccess lastSA= (StreetAccess)streetAccesses.lastElement();
		if (lastSA.street.equals(streetAndNumber[0]))
		{
			if (lastSA.minNumber==0)
				lastSA.minNumber=minMaxNumber[0];
			else if (minMaxNumber[0]!=0)
				lastSA.minNumber= Math.min(lastSA.minNumber, minMaxNumber[0]);
			lastSA.maxNumber= Math.max(lastSA.maxNumber, minMaxNumber[1]);
			streetAccesses.remove(streetAccesses.size()-1);
			streetAccesses.add(lastSA);
			return false;
		}
		
		StreetAccess currentSA= new StreetAccess();
		currentSA.street=streetAndNumber[0];
		currentSA.inTimestamp=timestamp;
		currentSA.minNumber=minMaxNumber[0];
		currentSA.maxNumber=minMaxNumber[1];
		streetAccesses.add(currentSA);
		return true;
	}
	
	public int intersectionsOfAddress(int index){
		int intersections=0;
		if (index<3 || index>= streetAccesses.size())
			return intersections;

		StreetAccess currentSA= (StreetAccess)streetAccesses.get(index);
		StreetAccess oldSA;

		for (int i=index-2; i>=0; i--)
		{
			oldSA=(StreetAccess)streetAccesses.get(i);
			if (oldSA.street.equals(currentSA.street)
					&& matchingNumbers (oldSA.minNumber, oldSA.maxNumber, 
							currentSA.minNumber, currentSA.maxNumber))
			{
				if (i==index-2)
					return intersectionsOfAddress(i);
				else
					return 1+ intersectionsOfAddress(i);
			}
		}
		return intersections;
	}
	
	public int intersectionOfLastAddress(){
		return intersectionsOfAddress(streetAccesses.size()-1);
	}
	
	public void removeOldAddresses (int minutes){
		if (minutes==0)
		{
			streetAccesses.clear();
			return;
		}
		long msecs=minutes*60000;
		long now=System.currentTimeMillis();
		
		StreetAccess tempSA;
		for (int i=0; i<streetAccesses.size(); i++){
			tempSA= (StreetAccess)streetAccesses.get(i);
			if (now-tempSA.inTimestamp>msecs)
			{
				streetAccesses.remove(i);
				i--;
			}
			else
				break;
		}
		
		
	}
	
	private boolean matchingNumbers (int minA, int maxA,int minB, int maxB){
		if (minA>maxA || minB>maxB)
			return false;
		if (minA==maxA || minB==maxB)
			return true;
		if (minB<=maxA || minA<=maxB)
			return true;
		return false;
	}
	
	
	private int[] parseNumber (String number){
		if (number==null || number.length()==0)
			return null;
		int dashIndex= number.indexOf("-");
		
		if (dashIndex==-1)
		{
			int result = parseSingleNumber(number);
			return new int []{result, result};
		}
		
		int result1=parseSingleNumber(number.substring(0, dashIndex));
		int result2=parseSingleNumber(number.substring(dashIndex+1));
		if (result1==0)
			return new int []{result2,result2};
		if (result2==0)
			return new int []{result1,result1};
		if (result1<result2)
			return new int []{result1,result2};
		return new int []{result2, result1};
	}
	
	private int parseSingleNumber(String singleNumber){
		if (singleNumber==null || singleNumber.length()==0)
			return 0;
		int lenght=singleNumber.length();
		char[] charArray =singleNumber.toCharArray();
		int offset=0;
		for (int i=0; i<lenght; i++)
			if (charArray[i]<'0' || charArray[i]>'9')
				offset++;
			else
				break;

		int count=lenght-offset;

		for (int i=lenght-1; i>=0; i--)
			if (charArray[i]<'0' || charArray[i]>'9')
				count--;
			else
				break;

		if (count<=0 || offset>=lenght)
			return 0;

		String stringNumber= new String (charArray,offset,count);

		try 
		{
			int number= Integer.parseInt(stringNumber);
			return number;
		}
		catch(NumberFormatException e)
		{
			return 0;
		}
	}
}
