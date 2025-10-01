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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Alerta;
import domain.Ride;
import domain.Traveler;
import exceptions.MoneyDatabaseException;
import exceptions.NoCashException;
import exceptions.NotEnoughSeatsException;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.alertAlreadyExists;
import exceptions.bookAlreadyExistException;

import javax.swing.JTextField;


public class TravelerGUI extends JFrame {
	private Traveler traveler;
	private static final long serialVersionUID = 1L;
	private JComboBox<String> jComboBoxOrigin = new JComboBox<String>();
	DefaultComboBoxModel<String> originLocations = new DefaultComboBoxModel<String>();

	private JComboBox<String> jComboBoxDestination = new JComboBox<String>();
	DefaultComboBoxModel<String> destinationCities = new DefaultComboBoxModel<String>();

	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"));
	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Rides"));
	private final JLabel jLabelSaldo;
	
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
	
	private JButton bookButton;

	private String[] columnNamesRides; 
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu menu; 
	
	private final JMenuItem diruaItem;
	private final JMenuItem languageItem;
	private final JMenuItem myBooks;
	private final JMenuItem logOut;
	private final JMenuItem myMovements;
	private final JMenuItem myReviews;
	private final JMenuItem reportsReceived;
	private final JMenuItem reportsCreated;
	private final JMenuItem deleteUser;
	private JLabel jLabelMsg=new JLabel();
		
	private static BLFacade appFacadeInterface;
	private JTextField textField;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}

	public TravelerGUI(Traveler t)
	{
		
		super();
		LocalDateTime dataOrdua=LocalDateTime.now();
        DateTimeFormatter formatoa=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataOrduaFormateatuta=dataOrdua.format(formatoa);
        
      //JOptionPane.showMessageDialog(null, "!Nuevo viaje disponible!", "Alerta de viaje", JOptionPane.INFORMATION_MESSAGE);
       
		traveler=t;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 600));
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
		
		jLabelMsg.setBounds(new Rectangle(141, 443, 225, 20));
		this.getContentPane().add(jLabelMsg, null);
		
		jLabelSaldo=new JLabel (ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.yourBalance")+" "+HasierakoGUI.getBusinessLogic().eskuratuDirua(t.getNAN()));
		jLabelSaldo.setBounds(5,5,110,20);
		this.getContentPane().add(jLabelSaldo, null);
		
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
		
		bookButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.Book"));
		bookButton.setEnabled(false);
		bookButton.setBounds(203, 494, 89, 23);
		getContentPane().add(bookButton);
		
		
		/**
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
							try {
								Alerta a=HasierakoGUI.getBusinessLogic().createAlert((String)originLocations.getSelectedItem(),(String)destinationCities.getSelectedItem() , UtilDate.trim(jCalendar1.getDate()), t.getNAN());
							}catch(alertAlreadyExists e1) {
								System.out.println(e1.getMessage());
							}catch(RideMustBeLaterThanTodayException e1) {
								System.out.println(e1.getMessage());
							}
							
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
					tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
					tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(3)); // not shown in JTable

				}
				jLabelMsg.setText("");
				bookButton.setEnabled(false);
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
		
		
		
		textField = new JTextField();
		//textField.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.NumberOfSeats"));
		textField.setBounds(264, 463, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.NumberOfSeats")); 
		lblNewLabel.setBounds(154, 463, 105, 20);
		getContentPane().add(lblNewLabel);
	
		menuBar.add(Box.createHorizontalGlue()); 
		menu=new JMenu();
		ImageIcon accountIcon = new ImageIcon("./ikonoak/accountImg.png");
		Image accountIconEskalatuta = accountIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
	
		
		menu.setIcon(new ImageIcon(accountIconEskalatuta));
		
		menu.getPopupMenu().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menu.getPopupMenu().setInvoker(menu);
		
		ImageIcon diruaIcon= new ImageIcon("./ikonoak/dirua.png");
		Image diruaIconEskalatuta = diruaIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		diruaItem = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("DiruaGUI.sartuDirua"));
		diruaItem.setIcon(new ImageIcon(diruaIconEskalatuta));
		menu.add(diruaItem);
		diruaItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new DiruaGUI(traveler);
				a.setVisible(true);
				a.setLocationRelativeTo(null);
				dispose();
			}
		});
		
		ImageIcon erreserbakIcon= new ImageIcon("./ikonoak/bidaiak.png");
		Image erreserbakIconEskalatuta = erreserbakIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		myBooks = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.myBooks"));
		myBooks .setIcon(new ImageIcon(erreserbakIconEskalatuta));
		menu.add(myBooks );
		myBooks .addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new MyBooksGUI(t);
				a.setVisible(true);
				a.setLocationRelativeTo(null);
				//setVisible(false);
				dispose();
				
			}
		});
		ImageIcon myMovementsIcon= new ImageIcon("./ikonoak/mugimendua.png");
		Image myMovementsIconEskalatuta = myMovementsIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		myMovements = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.myMovements"));
		myMovements .setIcon(new ImageIcon(myMovementsIconEskalatuta));
		menu.add(myMovements);
		myMovements .addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new MyMovementsTravelerGUI(t);
				a.setVisible(true);
				a.setLocationRelativeTo(null);
				//setVisible(false);
				dispose();
				
			}
		});
		ImageIcon hizkuntzaIcon= new ImageIcon("./ikonoak/hizkuntza.png");
		Image hizkuntzaIconEskalatuta = hizkuntzaIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		languageItem = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Language"));
		languageItem.setIcon(new ImageIcon(hizkuntzaIconEskalatuta));
		menu.add(languageItem);
		languageItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new LanguageTravelerGUI(t);
				a.setVisible(true);
				a.setLocationRelativeTo(null);
				//setVisible(false);
				dispose();
				
			}
		});
		ImageIcon myReviewsIcon= new ImageIcon("./ikonoak/myReviews.png");
		Image myReviewsIconEskalatuta = myReviewsIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		myReviews = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("MyReviewsGUI.myReviews"));
		myReviews .setIcon(new ImageIcon(myReviewsIconEskalatuta));
		menu.add(myReviews);
		myReviews .addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new MyReviewsGUI(t);
				a.setVisible(true);
				a.setLocationRelativeTo(null);
				//setVisible(false);
				dispose();
				
			}
		});
		
		ImageIcon  reportsReceivedIcon= new ImageIcon("./ikonoak/reportReceived.png");
		Image reportsReceivedIconEskalatuta =  reportsReceivedIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		reportsReceived = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.reportsReceived"));
		reportsReceived .setIcon(new ImageIcon( reportsReceivedIconEskalatuta));
		menu.add(reportsReceived);
		reportsReceived .addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new ReportsReceivedTGUI(t);
				a.setVisible(true);
				a.setLocationRelativeTo(null);
				//setVisible(false);
				dispose();
				
			}
		});
		
		
		 ImageIcon  reportsCreatedIcon= new ImageIcon("./ikonoak/createReport.png");
			Image reportsCreatedIconEskalatuta =  reportsCreatedIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
			 reportsCreated = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.reportsCreated"));
			 reportsCreated .setIcon(new ImageIcon( reportsCreatedIconEskalatuta));
			menu.add( reportsCreated);
			 reportsCreated .addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new ReportsCreatedTGUI(t);
					a.setVisible(true);
					a.setLocationRelativeTo(null);
					//setVisible(false);
					dispose();
					
				}
			});
			 
			 ImageIcon  deleteUserIcon= new ImageIcon("./ikonoak/deleteUser.png");
				Image deleteUserIconEskalatuta =  deleteUserIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
				deleteUser = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.deleteUser"));
				deleteUser .setIcon(new ImageIcon( deleteUserIconEskalatuta));
				menu.add( deleteUser);
				deleteUser .addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						JFrame a = new DeleteUserTGUI(t);
						a.setVisible(true);
						a.setLocationRelativeTo(null);
						//setVisible(false);
						dispose();
						
					}
				});
				
			 
		ImageIcon logOutIcon= new ImageIcon("./ikonoak/logOut.png");
		Image logOutIconEskalatuta = logOutIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		logOut = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.logOut"));
		logOut .setIcon(new ImageIcon(logOutIconEskalatuta));
		menu.add(logOut);
		logOut .addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				HasierakoGUI.getBusinessLogic().updateLastLogged(t.getNAN(), dataOrduaFormateatuta);
				System.out.println(t.getLastLogged());
				JFrame a = new HasierakoGUI();
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
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.MainTitle")+ " - "+traveler.getIzena());
		tableRides.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent event) {
		    	bookButton.setEnabled(true);
		    	/**
		        if (!event.getValueIsAdjusting()) { 
		            int selectedRow = tableRides.getSelectedRow(); 
		            
		            if (selectedRow != -1) { 
		                Ride r =(Ride) tableModelRides.getValueAt(selectedRow, 3); 
		                //System.out.println(r.getFrom());
		            }
		        }
		        */
		    }
		});
		bookButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				jLabelMsg.setForeground(Color.BLACK);
				jLabelMsg.setText("");
				try {
					int seats=Integer.parseInt(textField.getText());
					int selectedRow=tableRides.getSelectedRow();
					if (selectedRow != -1) {
						Ride r=(Ride) tableModelRides.getValueAt(selectedRow,3);
						HasierakoGUI.getBusinessLogic().createBook(t.getNAN(),r.getRideNumber(),seats);
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.Booked"));
						tableModelRides.setDataVector(null, columnNamesRides);
						bookButton.setEnabled(false);
						textField.setText("");
					}else {
						jLabelMsg.setForeground(Color.RED);
		                jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.NoRideSelected"));
					}
					
				} catch (bookAlreadyExistException e1) {
					jLabelMsg.setForeground(Color.RED);
					jLabelMsg.setText(e1.getMessage());
				}catch(NoCashException e2) {
					jLabelMsg.setForeground(Color.RED);
					jLabelMsg.setText(e2.getMessage());
				}catch(NotEnoughSeatsException e3) {
					jLabelMsg.setForeground(Color.RED);
					jLabelMsg.setText(e3.getMessage());
				}catch(NumberFormatException e1) {
					jLabelMsg.setForeground(Color.RED);
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("DiruaGUI.NumberFormatExcpetion"));
				}
				jLabelSaldo.setText(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.yourBalance")+" "+HasierakoGUI.getBusinessLogic().eskuratuDirua(t.getNAN()));
			}
		});

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
	/**
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	*/

}


