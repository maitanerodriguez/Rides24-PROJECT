package gui;

import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Driver;
import domain.Kotxe;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.moreThanCarSeatsException;

import javax.swing.GroupLayout.Alignment;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class SortuBidaiaGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	
	private Driver driver;
	private JTextField fieldOrigin=new JTextField();
	private JTextField fieldDestination=new JTextField();
	
	private JComboBox<String> jComboBoxCar = new JComboBox<String>();
	DefaultComboBoxModel<String> cars = new DefaultComboBoxModel<String>();
	
	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo")); 
	private JLabel jLabelSeats = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.NumberOfSeats"));
	private JLabel jLabRideDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));
	private JLabel jLabelPrice = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Price"));
	private JLabel JLabelCar=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.car"));
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));

	
	
	private JTextField jTextFieldSeats = new JTextField();
	private JTextField jTextFieldPrice = new JTextField();

	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.CreateRide"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	
	private List<Date> datesWithEventsCurrentMonth;


	public SortuBidaiaGUI(Driver driver) {

		this.driver=driver;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 597));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.CreateRide"));
		setResizable(false);

		jLabelOrigin.setBounds(new Rectangle(126, 102, 92, 20));
		jLabelSeats.setBounds(new Rectangle(126, 164, 173, 20));
		jTextFieldSeats.setBounds(new Rectangle(237, 164, 113, 20));
		
		jLabelPrice.setBounds(new Rectangle(126, 195, 178, 20));
		jTextFieldPrice.setBounds(new Rectangle(237, 195, 113, 20));
		
		JLabelCar.setBounds(new Rectangle(126, 226, 118, 20));
		/*
		jLabelEventDate.setBounds(125, 95, 259, 15);
		this.getContentPane().add(jLabelEventDate, null);
		//jButtonBilatu.setBounds(180,260,120,30);
		List<String> origins=HasierakoGUI.getBusinessLogic().getDepartCities();
		jLabelEvents.setBounds(77, 270, 259, 16);
		this.getContentPane().add(jLabelEvents);
		*/
		jCalendar.setBounds(new Rectangle(125, 300, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(168, 476, 130, 30));

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		

		jLabelMsg.setBounds(new Rectangle(126, 257, 305, 20));
		jLabelMsg.setForeground(Color.red);

		jLabelError.setBounds(new Rectangle(125, 169, 320, 20));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		//this.getContentPane().add(jLabelError, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jTextFieldSeats, null);

		this.getContentPane().add(jLabelSeats, null);
		this.getContentPane().add(jLabelOrigin, null);
		this.getContentPane().add(JLabelCar, null);

		

		this.getContentPane().add(jCalendar, null);
		jCalendar.getDayChooser().getDayPanel().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		this.getContentPane().add(jLabelPrice, null);
		this.getContentPane().add(jTextFieldPrice, null);
		
		List<Kotxe> carss=HasierakoGUI.getBusinessLogic().getCars(driver.getNAN());
		for(Kotxe c:carss)cars.addElement(c.getMatrikula());
		jComboBoxCar.setModel(cars);
		jComboBoxCar.setBounds(new Rectangle(237, 226, 113, 20));
		this.getContentPane().add(jComboBoxCar, null);
		
		BLFacade facade = HasierakoGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getThisMonthDatesWithRides("a","b",jCalendar.getDate());		
		
		jLabRideDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabRideDate.setBounds(125, 278, 140, 25);
		getContentPane().add(jLabRideDate);
		
		jLabelDestination.setBounds(128, 135, 61, 16);
		getContentPane().add(jLabelDestination);
		
		
		fieldOrigin.setBounds(237, 102, 113, 20);
		getContentPane().add(fieldOrigin);
		fieldOrigin.setColumns(10);
		
		
		fieldDestination.setBounds(237, 133, 113, 20);
		getContentPane().add(fieldDestination);
		fieldDestination.setColumns(10);
		 //Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//			
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar est� 30 de enero y se avanza al mes siguiente, devolver� 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este c�digo se dejar� como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar.setCalendar(calendarAct);						
	
					}
					jCalendar.setCalendar(calendarAct);
					int offset = jCalendar.getCalendar().get(Calendar.DAY_OF_WEEK);
					
						if (Locale.getDefault().equals(new Locale("es")))
							offset += 4;
						else
							offset += 5;
				Component o = (Component) jCalendar.getDayChooser().getDayPanel().getComponent(jCalendar.getCalendar().get(Calendar.DAY_OF_MONTH) + offset);
				}}});
		jButtonBack.setBounds(194, 517, 80,30);
		this.getContentPane().add(jButtonBack,null);
		jButtonBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new DriverGUI(driver);
				a.setVisible(true);
				a.setLocationRelativeTo(null);
				dispose();
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
		ImageIcon sortuBidaiakIcon= new ImageIcon("./ikonoak/createRide.png");
		Image sortuBidaiakIconEskalatuta = sortuBidaiakIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		JLabel label=new JLabel(new ImageIcon(sortuBidaiakIconEskalatuta));
		label.setBounds(224,11,50,50);
		getContentPane().add(label);
		
	}	 
	private void jButtonCreate_actionPerformed(ActionEvent e) {
		jLabelMsg.setText("");
		String error=field_Errors();
		if (error!=null) 
			jLabelMsg.setText(error);
		else
			try {
				BLFacade facade = HasierakoGUI.getBusinessLogic();
				int inputSeats = Integer.parseInt(jTextFieldSeats.getText());
				float price = Float.parseFloat(jTextFieldPrice.getText());

				Ride r=facade.createRide(fieldOrigin.getText(), fieldDestination.getText(), UtilDate.trim(jCalendar.getDate()), inputSeats, price, driver.getNAN(),(String)cars.getSelectedItem());
				//driver.getRideList().add(r);
				jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideCreated"));

			} catch (RideMustBeLaterThanTodayException e1) {
				// TODO Auto-generated catch block
				jLabelMsg.setText(e1.getMessage());
			} catch (RideAlreadyExistException e1) {
				// TODO Auto-generated catch block
				jLabelMsg.setText(e1.getMessage());
			}catch(moreThanCarSeatsException e1) {
				jLabelMsg.setText(e1.getMessage());
			}

		}
	

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	private String field_Errors() {
		
		try {
			if ((fieldOrigin.getText().length()==0) || (fieldDestination.getText().length()==0) || (jTextFieldSeats.getText().length()==0) || (jTextFieldPrice.getText().length()==0)||(cars.getSelectedItem()==null))
				return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorQuery");
			else {

				// trigger an exception if the introduced string is not a number
				int inputSeats = Integer.parseInt(jTextFieldSeats.getText());

				if (inputSeats <= 0) {
					return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.SeatsMustBeGreaterThan0");
				}
				else {
					float price = Float.parseFloat(jTextFieldPrice.getText());
					if (price <= 0) 
						return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.PriceMustBeGreaterThan0");
					
					else 
						return null;
						
				}
			}
		} catch (java.lang.NumberFormatException e1) {

			return  ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorNumber");		
		} catch (Exception e1) {

			e1.printStackTrace();
			return null;

		}
	}
}

