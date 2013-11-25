package na.utils;

public class MiniCalendar {
    public MiniCalendar() {

    }

    protected MiniCalendar(String showTime) {
	String parts[] = showTime.split("-");
	year = new Integer(parts[2]).intValue();
	month = new Integer(parts[1]).intValue();
	day = new Integer(parts[0]).intValue();
	hour = new Integer(parts[3]).intValue();
	minute = new Integer(parts[4]).intValue();
    }

    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;
}