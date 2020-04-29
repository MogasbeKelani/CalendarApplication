package hw4;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

public class TimeInterval {
	private Event check;
	/**
	 * Default
	 */
	public TimeInterval()
	{}
	/**
	 * @param e event being checked
	 */
	public TimeInterval(Event e)
	{
		check=e;
	}
	/**
	 * @param m map that stores both
	 * @param events being checked
	 * @return true if all reoccuring can be added
	 */
	public boolean checker(Map<Event,String> m,Map<Event,String> events)
	{
		TreeMap<Event,String>temp=new TreeMap<Event,String>();
		
		for(Event key: events.keySet())
		{
			temp.put(key, "");
		}
		for(Event key: m.keySet())
		{
			temp.put(key, "");
		}
		if(temp.size()==m.size()+events.size())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * @return a map with all the dates over the time period
	 */
	public Map<Event,String> reOccuringDates()
	{
		Calendar start = new GregorianCalendar(check.year,check.month-1,check.day,check.starthourmin/100,
				check.starthourmin%100);
		Map<Event,String> m = new TreeMap<Event,String>();
		Calendar end = new GregorianCalendar(check.year2,check.month2-1,check.day2,check.starthourmin/100,check.starthourmin%100);
		while(start.compareTo(end)<0)
		{
			if(check.reoccuring[start.get(start.DAY_OF_WEEK)-1]==1)
			{
				//System.out.println(start.getTime());
				Event d= new Event(start.get(start.YEAR),start.get(start.MONTH)+1,start.get(start.DAY_OF_MONTH),
						check.starthourmin/100,check.starthourmin%100,check.endhourmin/100,check.endhourmin%100);
				d.opt=1;
				d.reoccuring=check.reoccuring;
				d.day2=check.day2;
				d.month2=check.month2;
				d.year2=check.year2;
				m.put(d, d.toString());
			}
			start.add(start.DATE, 1);
		}
		if(m.isEmpty())
		{
			System.out.println("There are no corresponding dates within this time period");
		}
		return m;
	}
}
