package hw4;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SimpleCalendar {
	static Calendar calendar;
	private static Map<String, String> map = new HashMap<String, String>() {
		{
			put("12am", "0");
			put("1am", "1");
			put("2am", "2");
			put("3am", "3");
			put("4am", "4");
			put("5am", "5");
			put("6am", "6");
			put("7am", "7");
			put("8am", "8");
			put("9am", "9");
			put("10am", "10");
			put("11am", "11");
			put("12pm", "12");
			put("1pm", "13");
			put("2pm", "14");
			put("3pm", "15");
			put("4pm", "16");
			put("5pm", "17");
			put("6pm", "18");
			put("7pm", "19");
			put("8pm", "20");
			put("9pm", "21");
			put("10pm", "22");
			put("11pm", "23");
		}
	};

	public static void main(String[] args) throws IOException {

		// Frame Created
		JFrame frame = new JFrame();
		frame.setSize(1500, 500);

		// Calendar Model
		MyCalendar m = new MyCalendar();

		// Calendar Instance Set
		calendar = Calendar.getInstance();
		

		// Month Buttons Set
		ArrayList<JButton> daysOfMonth = new ArrayList<JButton>();

		// Month Panel Set
		JPanel monthPanel = new JPanel();
		dates(daysOfMonth, monthPanel, calendar);

		// Left Button Set and Finished
		JButton btnLeft = new JButton("<");
		btnLeft.setForeground(Color.white);
		btnLeft.setBackground(Color.red.darker());
		btnLeft.setOpaque(true);
		btnLeft.setBorderPainted(false);
		btnLeft.setBounds(300, 5, 60, 40);
		
		//Event TextAreas Initializer 
		JTextArea[] eventComp = new JTextArea[24];
		String [] eve= new String[24];
		for (int i = 0; i < 24; i++) {
			eve[i]="";
			eventComp[i] = new JTextArea();
		}
		//DataModel Instance Set
		DataModel d= new DataModel(eve);
		// Event panel created along with scroll function
		JPanel eventPanel = eventHolder(eventComp, m);
		eventChange(eventComp, m, d);
		JScrollPane scrollPane = new JScrollPane(eventPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(400, 100, 1000, 300);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.red.darker()));

		// Event day Edited and initial actionListeners set
		JLabel date = new JLabel(new SimpleDateFormat("MMMM YYYY").format(Calendar.getInstance().getTime()));
		date.setBounds(0, 100, 150, 15);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // get day of week for 1st of month
		String week[] = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		JTextField dayWeek = new JTextField(
				week[dayOfWeek] + " " + (calendar.get(calendar.MONTH) + 1) + "/" + calendar.get(calendar.DAY_OF_MONTH));
		for (int i = 0; i < daysOfMonth.size(); i++) {
			int t = i;
			daysOfMonth.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					calendar.set(calendar.DAY_OF_MONTH, Integer.valueOf(daysOfMonth.get(t).getText()));
					int dk = calendar.get(Calendar.DAY_OF_WEEK) - 1;
					dayWeek.setText(week[dk] + " " + (calendar.get(calendar.MONTH) + 1) + "/"
							+ calendar.get(calendar.DAY_OF_MONTH));
					try {
						eventChange(eventComp, m,d);
						eventPanel.repaint();
						scrollPane.repaint();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for (int k = 0; k < daysOfMonth.size(); k++) {
						if (daysOfMonth.get(k).isOpaque()) {
							daysOfMonth.get(k).setBackground(Color.white);
							daysOfMonth.get(k).setOpaque(false);
							daysOfMonth.get(t).setBackground(Color.red.darker());
							daysOfMonth.get(t).setOpaque(true);
							break;
						}
					}
					
				}
				
			});
		}
		// Action Listener for left Button
		btnLeft.addActionListener(new ActionListener() {
			boolean test = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				int month = calendar.get(calendar.MONTH);
				calendar.add(calendar.DATE, -1);
				if (month != calendar.get(calendar.MONTH)) {
					test = true;
					calendar.set(calendar.DAY_OF_MONTH, calendar.getActualMaximum(calendar.DAY_OF_MONTH));
					date.setText(new SimpleDateFormat("MMMM YYYY").format(calendar.getTime()));
					dates(daysOfMonth, monthPanel, calendar);
					for (int i = 0; i < daysOfMonth.size(); i++) {
						int w = i;
						daysOfMonth.get(i).addActionListener(new ActionListener() {
							// ActionListeners for the month buttons set
							@Override
							public void actionPerformed(ActionEvent e) {
								Calendar temp = calendar;
								temp.set(temp.DAY_OF_MONTH, Integer.valueOf(daysOfMonth.get(w).getText()));
								int dk = calendar.get(calendar.DAY_OF_WEEK) - 1;
								dayWeek.setText(week[dk] + " " + (calendar.get(calendar.MONTH) + 1) + "/"
										+ calendar.get(calendar.DAY_OF_MONTH));
								try {
									eventChange(eventComp, m,d);
									eventPanel.repaint();
									scrollPane.repaint();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								for (int k = 0; k < daysOfMonth.size(); k++) {
									if (daysOfMonth.get(k).isOpaque()) {
										daysOfMonth.get(k).setBackground(Color.white);
										daysOfMonth.get(k).setOpaque(false);
										daysOfMonth.get(w).setBackground(Color.red.darker());
										daysOfMonth.get(w).setOpaque(true);
										break;
									}
								}
							}

						});
					}
				}
				String day2 = "" + calendar.get(calendar.DAY_OF_MONTH);
				int day = 0;
				while (!daysOfMonth.get(day).getText().equals(day2)) {
					day++;
				}
				if (test == true) {
					test = false;
				} else {
					daysOfMonth.get(day + 1).setBackground(Color.white);
					daysOfMonth.get(day + 1).setOpaque(false);
				}
				daysOfMonth.get(day).setBackground(Color.red.darker());
				daysOfMonth.get(day).setOpaque(true);

				int dk = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				dayWeek.setText(week[dk] + " " + (calendar.get(calendar.MONTH) + 1) + "/"
						+ calendar.get(calendar.DAY_OF_MONTH));
				try {
					eventChange(eventComp, m,d);
					eventPanel.repaint();
					scrollPane.repaint();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
		// Right Button Set and Finished
		JButton btnRight = new JButton(">");
		btnRight.setForeground(Color.white);
		btnRight.setBackground(Color.red.darker());
		btnRight.setOpaque(true);
		btnRight.setBorderPainted(false);
		btnRight.setBounds(361, 5, 60, 40);
		btnRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int month = calendar.get(calendar.MONTH);
				calendar.add(calendar.DATE, 1);
				if (month != calendar.get(calendar.MONTH)) {
					date.setText(new SimpleDateFormat("MMMM YYYY").format(calendar.getTime()));
					dates(daysOfMonth, monthPanel, calendar);
					for (int i = 0; i < daysOfMonth.size(); i++) {
						int w = i;
						daysOfMonth.get(i).addActionListener(new ActionListener() {
							// ActionListeners for the month buttons set
							@Override
							public void actionPerformed(ActionEvent e) {
								Calendar temp = calendar;
								temp.set(temp.DAY_OF_MONTH, Integer.valueOf(daysOfMonth.get(w).getText()));
								int dk = calendar.get(calendar.DAY_OF_WEEK) - 1;
								dayWeek.setText(week[dk] + " " + (calendar.get(calendar.MONTH) + 1) + "/"
										+ calendar.get(calendar.DAY_OF_MONTH));
								try {
									eventChange(eventComp, m,d);
									eventPanel.repaint();
									scrollPane.repaint();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								for (int k = 0; k < daysOfMonth.size(); k++) {
									if (daysOfMonth.get(k).isOpaque()) {
										daysOfMonth.get(k).setBackground(Color.white);
										daysOfMonth.get(k).setOpaque(false);
										daysOfMonth.get(w).setBackground(Color.red.darker());
										daysOfMonth.get(w).setOpaque(true);
										break;
									}
								}
							}

						});
					}
				}
				String day2 = "" + calendar.get(calendar.DAY_OF_MONTH);
				int day = 0;
				while (!daysOfMonth.get(day).getText().equals(day2)) {
					day++;
				}
				daysOfMonth.get(day - 1).setBackground(Color.white);
				daysOfMonth.get(day - 1).setOpaque(false);
				daysOfMonth.get(day).setBackground(Color.red.darker());
				daysOfMonth.get(day).setOpaque(true);

				int dk = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				dayWeek.setText(week[dk] + " " + (calendar.get(calendar.MONTH) + 1) + "/"
						+ calendar.get(calendar.DAY_OF_MONTH));
				try {
					eventChange(eventComp, m,d);
					eventPanel.repaint();
					scrollPane.repaint();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
		// Quit button initialized along with ActionListener
		JButton btnQuit = new JButton("Quit");
		btnQuit.setForeground(Color.white);
		btnQuit.setBackground(Color.red.darker());
		btnQuit.setOpaque(true);
		btnQuit.setBorderPainted(false);
		btnQuit.setBounds(640, 5, 80, 40);
		btnQuit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					m.goodbye();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.dispose();
			}

		});
		// Frame for week days
		JLabel head = new JLabel("    S      M      T      W      T       F       S");
		head.setBounds(0, 120, 240, 15);
		JLabel temp = new JLabel();
		temp.setBounds(0, 140, 240, 200);

		// Create Button Set and Finished
		JButton btnCreate = new JButton("Create");
		btnCreate.setForeground(Color.white);
		btnCreate.setBackground(Color.red.darker());
		btnCreate.setOpaque(true);
		btnCreate.setBorderPainted(false);
		btnCreate.setBounds(5, 5, 100, 40);
		btnCreate.addActionListener(new ActionListener() {

			@Override public void actionPerformed(ActionEvent e) {
				JFrame popup = new JFrame();
				popup.setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				JTextField eventName = new JTextField("Your Event Name can be Entered Here");
				JPanel bottom = new JPanel();
				bottom.setLayout(new GridBagLayout());
				GridBagConstraints g = new GridBagConstraints();
				JTextField time1 = new JTextField("0" + (calendar.get(calendar.MONTH) + 1) + "/"
						+ calendar.get(calendar.DAY_OF_MONTH) + "/" + (calendar.get(calendar.YEAR) % 2000));
				JTextField time2 = new JTextField("10:30am");
				JLabel time3 = new JLabel("to");
				JTextField time4 = new JTextField("11:30am");
				JButton time5 = new JButton("Save");
				time5.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String[] arg = time1.getText().split("/");
						String[] arg2 = time2.getText().split(":");
						String[] arg3 = time4.getText().split(":");
						String start = map.get(arg2[0] + arg2[1].substring(arg2[1].length() - 2, arg2[1].length()));
						String end = map.get(arg3[0] + arg3[1].substring(arg3[1].length() - 2, arg3[1].length()));

						Event tevent = new Event(2000 + Integer.valueOf(arg[2]), Integer.valueOf(arg[0]),
								Integer.valueOf(arg[1]), Integer.valueOf(start),
								Integer.valueOf(arg2[1].substring(0, arg2[1].length() - 2)), Integer.valueOf(end),
								Integer.valueOf(arg3[1].substring(0, arg3[1].length() - 2)));
						boolean confirm = m.insert(tevent, eventName.getText());
						if (confirm == true) {
							m.events.put(tevent, eventName.getText());
							try {
								eventChange(eventComp, m,d);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							eventPanel.repaint();
							scrollPane.repaint();
							System.out.println("Success");
							popup.dispose();
						} else {
							System.out.println("Error did not add event: " + eventName.getText());

						}
					}

				});
				g.gridx = 0;
				g.gridy = 0;
				bottom.add(time1);
				g.gridy++;
				bottom.add(time2);
				g.gridy++;
				bottom.add(time3);
				g.gridy++;
				bottom.add(time4);
				g.gridy++;
				bottom.add(time5);

				c.gridx = 0;
				c.gridy = 0;
				popup.add(eventName, c);
				c.gridy++;
				popup.add(bottom, c);
				popup.pack();
				popup.setVisible(true);
			}

		});

		dayWeek.setBounds(700, 80, 500, 20);
		dayWeek.setOpaque(true);
		dayWeek.setBackground(frame.getBackground());
		dayWeek.setBorder(BorderFactory.createLineBorder(frame.getBackground()));

		frame.add(btnQuit);
		frame.add(btnRight);
		frame.add(btnLeft);
		frame.add(btnCreate);
		frame.add(dayWeek);
		frame.add(scrollPane);
		temp.add(monthPanel);
		frame.add(date);
		frame.add(head);
		frame.add(temp);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	/**
	 * @param daysOfMonth buttons for the month 
	 * @param calendarFrame Panel to create the calendar
	 * @param calendar instance 
	 */
	public static void dates(ArrayList<JButton> daysOfMonth, JPanel calendarFrame, Calendar calendar) {
		calendarFrame.removeAll();
		calendarFrame.setBorder(BorderFactory.createLineBorder(Color.red.darker()));
		calendarFrame.setBounds(0, 140, 240, 200);
		calendarFrame.setLayout(new GridLayout(0, 7));
		daysOfMonth.removeAll(daysOfMonth);
		Calendar temp = (Calendar) calendar.clone();
		temp.set(calendar.DAY_OF_MONTH, 1);
		int daysInMonth = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
		for (int i = 0; i < temp.get(temp.DAY_OF_WEEK) - 1; i++) {
			JButton o = new JButton();
			o.setOpaque(false);
			o.setContentAreaFilled(false);
			o.setBorderPainted(false);
			calendarFrame.add(o);
			daysOfMonth.add(o);
		}

		int temp2 = 1;
		for (int x = 0; x < daysInMonth; x++) {
			JButton o = new JButton("" + temp2);

			if (calendar.get(calendar.DAY_OF_MONTH) - 1 != x) {
				o.setFocusPainted(false);
				o.setBackground(Color.white);
			} else {
				o.setFocusPainted(false);
				o.setBackground(Color.white);
				o.setBackground(Color.red.darker());
				o.setOpaque(true);
			}

			calendarFrame.add(o);
			daysOfMonth.add(o);
			temp2++;

		}

	}

	/**
	 * @param btn stores all events
	 * @param mc get the events from the MyCalendar Instance
	 * @return a new JPanel with the new events of the day 
	 */
	public static JPanel eventHolder(JTextArea[] btn, MyCalendar mc) {
		// Panel that holds events
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagConstraints c = new GridBagConstraints();

		// natural height, maximum width
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 40;
		c.ipadx = 100;

		String[] ap = { "12 am", "1 am", "2 am", "3 am", "4 am", "5 am", "6 am", "7 am", "8 am", "9 am", "10 am",
				"11 am", "12 pm", "1 pm", "2 pm", "3 pm", "4 pm", "5 pm", "6 pm", "7 pm", "8 pm", "9 pm", "10 pm",
				"11 pm" };
		JButton[] timelist = new JButton[24];
		for (int i = 0; i < 24; i++) {
			timelist[i] = new JButton(ap[i]);
			timelist[i].setBorder(BorderFactory.createLineBorder(Color.red.darker()));
			timelist[i].setForeground(Color.black);
			timelist[i].setBackground(Color.white);
			panel.add(timelist[i], c);
			c.gridy++;

		}

		c.gridx = 1;
		c.gridy = 0;
		c.ipady = 8;
		c.ipadx = 900;
		for (int i = 0; i < 24; i++) {

			btn[i] = new JTextArea("\n\n");
			btn[i].setBackground(panel.getBackground());
			btn[i].setBorder(BorderFactory.createLineBorder(Color.red.darker()));
			panel.add(btn[i], c);
			c.gridy++;

		}
		return panel;
	}
	/**
	 * @param btn array of textAreas that store events
	 * @param mc myCalendar instance
	 * @throws IOException when events file cannot be found
	 */
	public static void eventChange(JTextArea[] btn, MyCalendar mc,DataModel d) throws IOException {

		String[] ap2 = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
				"17", "18", "19", "20", "21", "22", "23" };
		ArrayList<String> temp = mc.viewToday(calendar);
		String[] a = new String[24];
		for (int i = 0; i < temp.size(); i++) {
			String yo = temp.get(i);
			for (int k = 0; k < 24; k++) {
				if (temp.get(i).contains(" " + ap2[k] + ":")) {
					a[k] = temp.get(i);
					String line[]=temp.get(i).split("\\s+");
					System.out.println(line[line.length-3]);
					System.out.println(line[line.length-1]);
					if(line[line.length-3].contains(ap2[k] + ":") &&
							line[line.length-1].contains(ap2[k] + ":"))
					{
						
					}
					else
					{
						k++;
						while (k<23 && temp.get(i).contains(ap2[k] + ":") == false) {
							a[k] = temp.get(i);
							k++;
						}
						a[k] = temp.get(i);
						temp.set(i, "");
					}
					
				}

			}
			temp.set(i, yo);
		}

		ChangeListener c = new ChangeListener()
				{

					@Override public void stateChanged(ChangeEvent e) {
						try {
							d.setData(btn);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
			
				};
				d.attach(c);
				d.addData(a);


		}
	
}
