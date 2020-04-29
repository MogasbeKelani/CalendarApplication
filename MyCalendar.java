package hw4;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.time.*; 
import java.time.DayOfWeek; 

/**
 * @author mohammedmogasbe
 * This Class Manages TimeIntervals 
 */
public class MyCalendar {
	static TreeMap<Event,String> events;
	public static final String month[]= {"January","February","March","April","May","June","July",
	            "August","September","October","November","December"};
	public static final String week[]= {"Sunday","Monday", "Tuesday",
			"Wednesday", "Thursday", "Friday", "Saturday"};
	public static final Map<String, Integer> fileWeek= Map.of("S",0,"M",1,"T",2,"W",3,"R",4,"F",5,"A",6);
	/**
	 * Initializes Todays date & all events
	 * @throws IOException 
	 */
	public MyCalendar() throws IOException
	{
		events= new TreeMap<Event,String>();
		File f = new File("events.txt");
		if(!f.exists())
		{
			f.createNewFile();
		}
		Scanner temp = new Scanner(new File("events.txt"));
		
		while(temp.hasNext())
		{
			String name=temp.nextLine();
			String event=temp.nextLine();
			if(Character.isLetter(event.charAt(0)))
			{
				int a[]=new int[7];
				Arrays.fill(a, 0);
				String line[]= event.split("\\s+");
				for(int i=0;i<line[0].length();i++)
				{
					a[fileWeek.get(line[0].charAt(i)+"")]=1;
					//TR 10:30 11:45 1/23/20 5/7/20
				}
				String smh[]= line[1].split(":");
				String emh[]= line[2].split(":");
				String sd[]= line[3].split("/");
				String ed[]= line[4].split("/");
				//TimeInterval.TimeInterval(int[] a, int y, int m, int d, int y2, int m2, int d2,
				//int sh, int s, int eh, int e)
				Event t =new Event(a, Integer.valueOf(sd[2])+2000,Integer.valueOf(sd[0]),
						Integer.valueOf(sd[1]) ,Integer.valueOf(ed[2])+2000, 
						Integer.valueOf(ed[0]),Integer.valueOf(ed[1]), Integer.valueOf(smh[0]), 
						Integer.valueOf(smh[1]), Integer.valueOf(emh[0]), Integer.valueOf(emh[1]));
				this.insertAll(t, name);
			}
			else
			{
				//9/28/20 9:30 11:30
				
				String line[]= event.split("\\s+");

				String smh[]= line[1].split(":");
				String emh[]= line[2].split(":");
				String sd[]= line[0].split("/");
				//TimeInterval.TimeInterval(int[] a, int y, int m, int d, int y2, int m2, int d2,
				//int sh, int s, int eh, int e)
				//TimeInterval.TimeInterval(int y, int m, int d, int sh, int s, int eh, int e)
				Event t =new Event(Integer.valueOf(sd[2])+2000,Integer.valueOf(sd[0]),
						Integer.valueOf(sd[1]) , Integer.valueOf(smh[0]), 
						Integer.valueOf(smh[1]), Integer.valueOf(emh[0]), Integer.valueOf(emh[1]));
				this.insert(t, name);
			}
		}
		temp.close();
	}
	/**
	 * Prints the month with the Day of the Month Bracketed
	 */
	public void viewMonth()
	{
		Calendar calendar= Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); //get day of week for 1st of month
		int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int dom=calendar.get(Calendar.DAY_OF_MONTH);
		//print month name and year
		System.out.println(new SimpleDateFormat("MMMM YYYY").format(calendar.getTime()));
		System.out.println("Su Mo Tu We Th Fr Sa");

		//print initial spaces
		String initialSpace = "";
		for (int i = 0; i < dayOfWeek - 1; i++) {
		    initialSpace += "   ";
		}
		System.out.print(initialSpace);
		
		//print the days of the month starting from 1
		for (int i = 0, dayOfMonth = 1; dayOfMonth <= daysInMonth; i++) {
		    for (int j = ((i == 0) ? dayOfWeek - 1 : 0); j < 7 && (dayOfMonth <= daysInMonth); j++) 
		    {
		    	if(dom==dayOfMonth)
		    	{
		    		System.out.printf("%2s ", ""+dayOfMonth+"");
			        dayOfMonth++;
		    	}
		    	else
		    	{
		    		System.out.printf("%2d ", dayOfMonth);
			        dayOfMonth++;
		    	}
		        
		    }
		    System.out.println();
		}
		
     
	}
	/**
	 * @param calendar view Event by selected month using the calendar Instance
	 */
	public void viewEventMonth(Calendar calendar)
	{
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); //get day of week for 1st of month
		int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		//print month name and year
		System.out.println(new SimpleDateFormat("MMMM YYYY").format(calendar.getTime()));
		System.out.println(" S  M  T  W  T  F  S");

		//print initial spaces
		String initialSpace = "";
		for (int i = 0; i < dayOfWeek - 1; i++) {
		    initialSpace += "   ";
		}
		System.out.print(initialSpace);
		
		//print the days of the month starting from 1
		for (int i = 0, dayOfMonth = 1; dayOfMonth <= daysInMonth; i++) {
		    for (int j = ((i == 0) ? dayOfWeek - 1 : 0); j < 7 && (dayOfMonth <= daysInMonth); j++) 
		    {
		    	if(events.toString().contains(calendar.get(calendar.MONTH)+1+"/"+dayOfMonth+"/"+calendar.get(calendar.YEAR)))
		    	{
		    		System.out.printf("%2s ", "{"+dayOfMonth+"}");
			        dayOfMonth++;
		    	}
		    	else
		    	{
		    		System.out.printf("%2d ", dayOfMonth);
			        dayOfMonth++;
		    	}
		        
		    }
		    System.out.println();
		}
		
     
	}
	
	/**
	 * Checks if all possible date in the timeInterval can be added
	 * @param m
	 * @return true if events can be inserted
	 */
	public boolean checker(Map<Event,String> m)
	{
		return new TimeInterval().checker(m, events);
	}
	/**
	 * insertsAll events on the timeInterval perviously declared
	 * @param d
	 * @param name
	 * @returns true if the time interval can be added
	 */
	public boolean insertAll(Event d,String name)
	{
		Map<Event, String>temp2= new TreeMap<Event,String>();
		temp2=d.reOccuringDates();
		if(this.checker(temp2))
		{
			for(Event key: temp2.keySet())
			{
				events.put(key, name);
			}
			return true;
		}
		else
		{
			return false;
		}
		
		
	}
	/**
	 * Prints Formats for All Events in Order seperated by Event Type
	 */
	public void viewEvents()
	{
		if(events.isEmpty())
		{
			System.out.println("No Events Scheduled");
			return;
		}
		Set<String>s = new HashSet<String>();
		int yearC=0;
		int yearnum=0;
		System.out.println("ONE TIME EVENTS");
		for(Event d: events.keySet())
		{
			if(d.opt==0)
			{
				Calendar temp=Calendar.getInstance();
				String[] par2= d.toString().split("\\s+");
				String[] par= par2[0].split("/");
				temp.set(Integer.valueOf(par[2]), Integer.valueOf(par[0])-1, Integer.valueOf(par[1]));
				int mon= temp.get(temp.MONTH);
				int we= temp.get(temp.DAY_OF_WEEK)-1;
				if(d==events.firstKey())
				{
					yearC=Integer.valueOf(par[2]);
					System.out.println(yearC);
				}
				
				if(yearC!=Integer.valueOf(par[2]))
				{
					yearC=Integer.valueOf(par[2]);
					System.out.println("\n"+yearC);
				}
				if(d.opt==0 && !s.contains(events.get(d)))
				{
					System.out.println(week[we]+" "+month[mon]+" "+par[1]+" "+par2[1]+" - "+par2[2]+" "+events.get(d));
				}
			}
			
		}
		for(Event d: events.keySet())
		{
			
			if(d==events.firstKey())
			{
				System.out.println("\nRECURRING EVENTS\n");
			}
			
			if(d.opt==1 && !s.contains(events.get(d)))
			{
				System.out.println(d+" "+events.get(d));
				s.add(events.get(d));
			}
		}
	}
	/**
	 * Inserts an event
	 * @param d time interval
	 * @param n name of event
	 * @returns false if unable to add the event
	 */
	public boolean insert(Event d,String n)
	{
		if(events.get(d)==null)
		{
			events.put(d, n);
			return true;
		}
		return false;
	}
	/**
	 * @param n number of months to be traversed from current date
	 * @returns a Calendar instance with n increments of the Month
	 */
	public Calendar getStance(int n)
	{
		Calendar calendar= Calendar.getInstance();
		calendar.set(calendar.get(calendar.YEAR),calendar.get(calendar.MONTH)+n,calendar.get(calendar.DAY_OF_MONTH));
		return calendar;
	}
	/**
	 * @param d2 instance of calendar to collect events from 
	 * @return create an array of events on that day 
	 */
	public ArrayList<String> viewToday(Calendar d2)
	{
		ArrayList<String> holder= new ArrayList<String>();
		Set<String> temp= new TreeSet<String>();
		for(Event key: events.keySet())
		{
			String line[]=key.toString().split("\\s+");
			if(line[0].contains(d2.get(d2.MONTH)+1+"/"+d2.get(d2.DAY_OF_MONTH)+"/"+d2.get(d2.YEAR)))
			{
				String[] cip= key.toString().split("\\s+");
				
				holder.add(0, events.get(key)+": "+cip[1]+" - "+cip[2]);
				temp.add(events.get(key));
			}
		}
		return holder;
	}

	/**
	 * Prints the events to event.txt
	 * @throws IOException
	 */
	public void goodbye()throws IOException
	{

		File f2 = new File("events.txt");
		f2.createNewFile();
		FileWriter insert2 = new FileWriter("events.txt");
		PrintWriter pw2 = new PrintWriter(insert2);
		Set<String> s= new HashSet<String>();
		for(Event keys: events.keySet())
		{
			if(!s.contains(events.get(keys)))
			{
				if(keys.opt==1)
				{
					String[] lines=keys.toString().split("\\s+");
					
					pw2.println(events.get(keys));
					pw2.println(lines[0]+" "+lines[1]+" "+lines[2]+" "
							+lines[3].substring(0,lines[3].length()-2)+" "+lines[4].substring(0,lines[4].length()-2));
				}
				if(keys.opt==0)
				{
					String[] lines=keys.toString().split("\\s+");
					
					pw2.println(events.get(keys));
					pw2.println(lines[0].substring(0,lines[0].length()-2)+" "+lines[1]+" "+lines[2]);
				}
				
			}
			s.add(events.get(keys));
		}
		pw2.close();
	}


}
