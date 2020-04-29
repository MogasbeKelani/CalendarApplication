package hw4;

import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author mohammedmogasbe
 * stores data and changelisteners
 */
public class DataModel {
	private static String[] data;
	private static ArrayList<ChangeListener> change; 
	public DataModel(String[] d)
	{
		data=d;
		change= new ArrayList<ChangeListener>();
		

	}
	/**
	 * data Storage
	 * @param a
	 */
	public void addData(String a[])
	{
		
		data=a;
		ChangeEvent c = new ChangeEvent(this);
		change.get(0).stateChanged(c);
	}
	/**
	 * Mutator
	 * @return textArea Format
	 */
	public static void setData(JTextArea[] btn) throws IOException {
		
		for (int i = 0; i < 24; i++) {
			if (data[i] == null) {
				data[i] = "";
			}
		}
		
		for (int i = 0; i < 24; i++) {

			String change[]= data[i].split(":");
			if(!data[i].isBlank())
			{
				btn[i].setText((change[0].substring(0,change[0].length()) + "\n\n"));
			}
			else
			{
				btn[i].setText(data[i] + "\n\n");

			}
		}
			
	}
	/**
	 * @param c add change listener
	 */
	public void attach(ChangeListener c)
	{
		change.add(c);
	}

}
