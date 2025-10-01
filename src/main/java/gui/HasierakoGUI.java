package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;


public class HasierakoGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JComboBox<String> jComboBoxOrigin = new JComboBox<String>();
	DefaultComboBoxModel<String> originLocations = new DefaultComboBoxModel<String>();

	private JComboBox<String> jComboBoxDestination = new JComboBox<String>();
	DefaultComboBoxModel<String> destinationCities = new DefaultComboBoxModel<String>();

	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"));
	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Rides")); 

	//private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	//private JButton jButtonBilatu=new JButton (ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Search"));

	// Code for JCalendar

	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();

	private List<Date> datesWithRidesCurrentMonth = new Vector<Date>();

	private JTable tableRides= new JTable();

	private DefaultTableModel tableModelRides;
	
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private final ButtonGroup buttonGroup = new ButtonGroup();


	private String[] columnNamesRides; 
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu menu; 
	private final JMenuItem registerItem;
	private final JMenuItem loginItem; 
	private final JMenuItem languageItem; 
		
	private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}

	public HasierakoGUI()
	{
		super();
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 570));
		setResizable(false);
		
		columnNamesRides= new String[] {
				ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Driver"), 
				ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.NPlaces"), 
				ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Price")
		};
		
		jLabelEventDate.setBounds(125, 95, 259, 15);
		this.getContentPane().add(jLabelEventDate, null);
		//jButtonBilatu.setBounds(180,260,120,30);
		List<String> origins=HasierakoGUI.getBusinessLogic().getDepartCities();
		jLabelEvents.setBounds(77, 270, 259, 16);
		this.getContentPane().add(jLabelEvents);
		
		for(String location:origins) originLocations.addElement(location);
		
		jLabelOrigin.setBounds(new Rectangle(125, 15, 100, 15));
		jLabelDestination.setBounds(125, 55, 100, 15);
		getContentPane().add(jLabelOrigin);
		getContentPane().add(jLabelDestination);

		jComboBoxOrigin.setModel(originLocations);
		jComboBoxOrigin.setBounds(new Rectangle(125, 30, 225, 20));
		

		List<String> aCities=HasierakoGUI.getBusinessLogic().getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
		for(String aciti:aCities) {
			destinationCities.addElement(aciti);
		}
		
		jComboBoxOrigin.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				destinationCities.removeAllElements();

				List<String> aCities=HasierakoGUI.getBusinessLogic().getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
				for(String aciti:aCities) {
					destinationCities.addElement(aciti);
				}
				tableModelRides.getDataVector().removeAllElements();
				tableModelRides.fireTableDataChanged();

				
			}
		});


		jComboBoxDestination.setModel(destinationCities);
		jComboBoxDestination.setBounds(new Rectangle(125, 70, 225, 20));
		jComboBoxDestination.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,	new Color(210,228,238));
				datesWithRidesCurrentMonth=HasierakoGUI.getBusinessLogic().getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);

			}
		});

		//this.getContentPane().add(jButtonBilatu,null);
		this.getContentPane().add(jComboBoxOrigin, null);

		this.getContentPane().add(jComboBoxDestination, null);


		jCalendar1.setBounds(new Rectangle(125, 110, 225, 150));

		/*
		rdbtnNewRadioButton = new JRadioButton("English", true);
		rdbtnNewRadioButton.setBounds(280,450,80,20);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();				}
		});
		buttonGroup.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton_1.setBounds(125,450,72,20);
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();				}
		});
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.setBounds(195,450,85,20);
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_2);
		getContentPane().add(rdbtnNewRadioButton_1);
		getContentPane().add(rdbtnNewRadioButton_2);
		getContentPane().add(rdbtnNewRadioButton);
		*/
		// Code for JCalendar
		jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					

					
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						

						jCalendar1.setCalendar(calendarAct);

					}
					
					try {
						tableModelRides.setDataVector(null, columnNamesRides);
						tableModelRides.setColumnCount(4); // another column added to allocate ride objects
						List<domain.Ride> rides=HasierakoGUI.getBusinessLogic().getRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),UtilDate.trim(jCalendar1.getDate()));

						if (rides.isEmpty() ) {
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.NoRides")+ ": "+dateformat1.format(calendarAct.getTime()));
						}else {
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Rides")+ ": "+dateformat1.format(calendarAct.getTime()));
						}
						for (domain.Ride ride:rides){
							Vector<Object> row = new Vector<Object>();
							row.add(ride.getDriver().getIzena());
							row.add(ride.getnPlaces());
							row.add(ride.getPrice());
							row.add(ride); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
							tableModelRides.addRow(row);		
						}
						datesWithRidesCurrentMonth=HasierakoGUI.getBusinessLogic().getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
						paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);


					} catch (Exception e1) {

						e1.printStackTrace();
					}
					tableRides.getColumnModel().getColumn(0).setPreferredWidth(140);
					tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
					tableRides.getColumnModel().getColumn(2).setPreferredWidth(30);
					tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(3)); // not shown in JTable

				}
			} 
			
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(77, 290, 346, 150));
		scrollPaneEvents.setViewportView(tableRides);
		tableModelRides = new DefaultTableModel(null, columnNamesRides);

		tableRides.setModel(tableModelRides);

		tableModelRides.setColumnIdentifiers(columnNamesRides);
		tableModelRides.setColumnCount(4); // another column added to allocate ride objects

		tableRides.getColumnModel().getColumn(0).setPreferredWidth(140);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);

		tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(3)); // not shown in JTable
		
		this.getContentPane().add(scrollPaneEvents, null);
	
		datesWithRidesCurrentMonth=HasierakoGUI.getBusinessLogic().getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
		paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);
	
		menuBar.add(Box.createHorizontalGlue()); 
		menu=new JMenu();
		ImageIcon accountIcon = new ImageIcon("./ikonoak/accountImg.png");
		Image accountIconEskalatuta = accountIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		
		ImageIcon registerIcon= new ImageIcon("./ikonoak/registerImg.png");
		Image registerIconEskalatuta = registerIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		
		ImageIcon loginIcon= new ImageIcon("./ikonoak/loginImg.png");
		Image loginIconEskalatuta = loginIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		
		ImageIcon hizkuntzaIcon= new ImageIcon("./ikonoak/hizkuntza.png");
		Image hizkuntzaIconEskalatuta = hizkuntzaIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		
		menu.setIcon(new ImageIcon(accountIconEskalatuta));
		
		menu.getPopupMenu().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menu.getPopupMenu().setInvoker(menu);
		
		registerItem = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Register"));
		registerItem.setIcon(new ImageIcon(registerIconEskalatuta));
		menu.add(registerItem);
		
		loginItem = new JMenuItem("log in");
		loginItem .setIcon(new ImageIcon(loginIconEskalatuta));
		menu.add(loginItem );
		
		languageItem = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Language"));
		languageItem.setIcon(new ImageIcon(hizkuntzaIconEskalatuta));
		menu.add(languageItem);
		
		loginItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new LoginGUI();
				a.setVisible(true);
				a.setLocationRelativeTo(null);
				dispose();
			}
		});
		registerItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new RegisterGUI();
				a.setVisible(true);
				a.setLocationRelativeTo(null);
				dispose();
			}
		});
		languageItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new LanguageGUI();
				a.setVisible(true);
				a.setLocationRelativeTo(null);
				//setVisible(false);
				dispose();
				
			}
		});
		JPopupMenu popup = menu.getPopupMenu();
        popup.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(() -> {
                    popup.setLocation(menu.getLocationOnScreen().x + menu.getWidth() - popup.getWidth(),
                    		menu.getLocationOnScreen().y + menu.getHeight());
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });
		
		menuBar.add(menu);
		
		setJMenuBar(menuBar);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.MainTitle")+ " - " +ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Unregistered"));

	}
	public static void paintDaysWithEvents(JCalendar jCalendar,List<Date> datesWithEventsCurrentMonth, Color color) {
		//		// For each day with events in current month, the background color for that day is changed to cyan.


		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);
		
		/**
		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
		*/
			offset += 5;


		for (Date d:datesWithEventsCurrentMonth){

			calendar.setTime(d);


			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
			//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(color);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);


	}
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	/**
	private void paintAgain() {
		jLabelOrigin.setText(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.LeavingFrom"));
		jLabelDestination.setText(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.GoingTo"));
		jLabelEventDate.setText(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.RideDate"));
		//jButtonBilatu.setText(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Search"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.MainTitle")+  ": "+ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Unregistered"));
		columnNamesRides[0]=(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Driver")); 
		columnNamesRides[1]=(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.NPlaces")); 
		columnNamesRides[2]=ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Price");
		tableModelRides.setColumnIdentifiers(columnNamesRides);
		tableModelRides.setColumnCount(4);
		tableRides.getColumnModel().getColumn(0).setPreferredWidth(140);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
		tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(3));
		registerItem.setText(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Register"));
	}
	*/

}

