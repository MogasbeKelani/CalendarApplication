package hw4;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

public class Event implements Comparable<Event> {
	protected int year;
	protected int month;
	protected int day;
	protected int year2;
	protected int month2;
	protected int day2;
	protected int starthourmin;
	protected int endhourmin;
	protected int[] reoccuring;
	public int opt;
	/**
	 * @param y year 
	 * @param m month
	 * @param d day
	 * @param sh starting hour
	 * @param s staring min
	 * @param eh ending hour 
	 * @param e ending min
	 */
	public Event(int y,int m,int d , int sh, int s,int eh,int e) 
	{
		starthourmin=(sh*100)+s;
		endhourmin=(eh*100)+e;
		year=y;
		month=m;
		day=d;
		opt=0;
	}
	/**
	 * @param a array with positions of the week
	 * @param y starting year
	 * @param m starting month
	 * @param d starting day
	 * @param y2 ending year
	 * @param m2 ending month
	 * @param d2 ending day
	 * @param sh starting hour
	 * @param s starting min
	 * @param eh ending hour
	 * @param e ending min
	 */
	public Event(int[]a,int y,int m,int d,int y2,int m2,int d2 , int sh, int s,int eh,int e) 
	{
		starthourmin=(sh*100)+s;
		endhourmin=(eh*100)+e;
		year=y;
		month=m;
		day=d;
		year2=y2;
		month2=m2;
		day2=d2;
		reoccuring= a;
		opt=1;
	}

	/**
	 * @returns a Map of all days of the new event
	 */
	public Map<Event,String> reOccuringDates()
	{
		return new TimeInterval(this).reOccuringDates();
	}
	/**
	 * @Overrides ToString method for storage purposes
	 */
	@Override public String toString()
	{
		String t=this.starthourmin%100+"";
		String t2=this.endhourmin%100+"";
		if(t.length()==1)
		{
			t+="0";
		}
		if(t2.length()==1)
		{
			t2+="0";
		}
		if(opt==0)
		{
			return  this.month+"/"+ this.day +"/"+(this.year)+" "+this.starthourmin/100+ 
					":"+t+ " "+this.endhourmin/100+ 
					":"+t2;
		}
		else
		{
			return  this.setOccurance()+" "+this.starthourmin/100+
					":"+t+" "+this.endhourmin/100+ 
					":"+t2+" "+this.month+"/"+ this.day +"/"+(this.year)+" "+this.month2+"/"+ 
							this.day2 +"/"+(this.year2);
		}
		
	}
	/**
	 * @Overrides compareTo for map purposes
	 */
	@Override public int compareTo(Event d) {
		if(this.year==d.year && this.month==d.month && this.day==d.day)
		{
			if(this.starthourmin<d.starthourmin && this.endhourmin<=d.starthourmin)
			{
				return -1;
			}
			if(this.starthourmin>=d.endhourmin && this.endhourmin>d.starthourmin)
			{
				return 1;
			}
			return 0;
		}
		if(this.year<d.year)
		{
			return -1;
		}
		else if(d.year<this.year)
		{
			return 1;
		}
		else
		{
			if(this.month<d.month)
			{
				return -1;
			}
			else if(d.month<this.month)
			{
				return 1;
			}
			else
			{
				if(this.day<d.day)
				{
					return -1;
				}
				else if(d.day<this.day)
				{
					return 1;
				}
			}
		}
		
		return 0;
	}
	/**
	 * @returns a String of the days of the week the event has
	 */
	public String setOccurance()
	{
		String store="";
		String[] at=new String[] {"S","M","T","W","R","F","A"};
		for(int i=0;i<at.length;i++)
		{
			if(reoccuring[i]==1)
			{
				store+=at[i];
			}
		}
		return store;
	}

}
