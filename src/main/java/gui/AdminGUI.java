package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
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
import domain.Admin;
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


public class AdminGUI extends JFrame {
	private Admin admin;
	private static final long serialVersionUID = 1L;

	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu menu; 
	
	private final JMenuItem languageItem;
	private final JMenuItem logOut;
	private final JMenuItem reports;
		
	private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}

	public AdminGUI(Admin ad)
	{
		super();
		admin=ad;
		this.getContentPane().setLayout(null);
		
		this.setSize(new Dimension(400, 184));
		setResizable(false);
	
	
		menuBar.add(Box.createHorizontalGlue()); 
		menu=new JMenu();
		ImageIcon accountIcon = new ImageIcon("./ikonoak/accountImg.png");
		Image accountIconEskalatuta = accountIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
	
		
		menu.setIcon(new ImageIcon(accountIconEskalatuta));
		
		menu.getPopupMenu().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menu.getPopupMenu().setInvoker(menu);
		
		ImageIcon reportsIcon= new ImageIcon("./ikonoak/report.png");
		Image reportsIconEskalatuta = reportsIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		reports = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.reports"));
		reports .setIcon(new ImageIcon(reportsIconEskalatuta));
		menu.add(reports);
		reports .addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
			
				JFrame a = new ReportsGUI(ad);
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
				JFrame a = new LanguageAdminGUI(ad);
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
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.MainTitle")+ " - "+admin.getIzena());
		
		ImageIcon adminIcon= new ImageIcon("./ikonoak/admin.png");
		Image adminIconEskalatuta = adminIcon.getImage().getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
		JLabel label=new JLabel(new ImageIcon(adminIconEskalatuta));
		label.setBounds(153,11,80,80);
		getContentPane().add(label);
	}
}



