package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtLogin;
	private  ButtonGroup buttonGroup1 = new ButtonGroup();
	//private static BLFacade appFacadeInterface;
	private JPasswordField pwdPassword;
	private JButton btnSistemanSartu;
	private JRadioButton rdbtnGidaria;
	private JRadioButton rdbtnBidaiaria;
	public Object jLabelSelectOption;
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
	private JLabel lblEzExistitu=new JLabel();
	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(400, 300));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Login"));
		setResizable(false);
		/**
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		*/
		
		txtLogin = new JTextField();
		txtLogin.setText("login");
		txtLogin.setBounds(110, 70, 180, 20);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		
		//JLabel lblErabiltzaileIzenaSartu = new JLabel("Erabiltzaile izena sartu");
		//blErabiltzaileIzenaSartu.setBounds(50, 29, 165, 16);
		//contentPane.add(lblErabiltzaileIzenaSartu);
		
		//JLabel lblPasahitzaSartu = new JLabel("Pasahitza sartu");
		//lblPasahitzaSartu.setBounds(50, 72, 145, 16);
		//contentPane.add(lblPasahitzaSartu);
		
		//JLabel lblErabiltzaileMotaAukeratu = new JLabel("Erabiltzaile mota aukeratu");
		//lblErabiltzaileMotaAukeratu.setBounds(30, 118, 165, 16);
		//contentPane.add(lblErabiltzaileMotaAukeratu);
		lblEzExistitu.setBounds(110,121,180 ,20 );
		getContentPane().add(lblEzExistitu);
		btnSistemanSartu = new JButton("Log in");
		btnSistemanSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblEzExistitu.setText("");
				String log=txtLogin.getText();
				String pass=new String(pwdPassword.getPassword());
				String mota="";
				User u=HasierakoGUI.getBusinessLogic().login(log, pass);
				if(u instanceof Driver) {
					JFrame a=new DriverGUI((Driver)u);
					a.setVisible(true);
					a.setLocationRelativeTo(null);
					setVisible(false);
					lblEzExistitu.setText("");
				}else if(u instanceof Traveler) {
					Traveler t=(Traveler)u;
					LocalDateTime dataOrdua=LocalDateTime.now();
			        DateTimeFormatter formatoa=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			        String dataOrduaFormateatuta=dataOrdua.format(formatoa);
					LocalDateTime now=LocalDateTime.parse(dataOrduaFormateatuta, formatoa);
			        List<Ride> rides=HasierakoGUI.getBusinessLogic().getRidesRelatedToAlerts(t.getNAN());
			        /*
			        for(Ride r: rides) {
			        	System.out.println(r.getFrom());
			        }
			        */
			        if(rides!=null&&t.getLastLogged()!=null) {
			        	LocalDateTime lastLogged=LocalDateTime.parse(t.getLastLogged(), formatoa);
			        	for (Ride r: rides) {
			        		LocalDateTime whenCreated=LocalDateTime.parse(r.getWhenCreated(), formatoa);
			        		if(lastLogged.isBefore(whenCreated)&&now.isAfter(whenCreated)) {
			        			JOptionPane.showMessageDialog(null, r.getDriver().getIzena()+" "+ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.hasCreated")+" "+r.getFrom()+" - " +r.getTo()+" "+r.getDate(), ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.rideNotification"), JOptionPane.INFORMATION_MESSAGE);
			        			// JOptionPane.showMessageDialog(null, "El conductor ha creado uno de los viajes que has buscado:  a  ", "Alerta de viaje", JOptionPane.INFORMATION_MESSAGE);
			        		}
			        	}
			        }
					JFrame a=new TravelerGUI((Traveler)u);
					a.setVisible(true);
					a.setLocationRelativeTo(null);
					setVisible(false);
					
					
					lblEzExistitu.setText("");
				}else if(u instanceof Admin) {
					JFrame a=new AdminGUI((Admin) u);
					a.setVisible(true);
					a.setLocationRelativeTo(null);
					setVisible(false);
					lblEzExistitu.setText("");
				}else {
					lblEzExistitu.setForeground(Color.RED);
					lblEzExistitu.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.EzExistitu"));
				}
				
			}
		});
		btnSistemanSartu.setBounds(136,145,120,30);
		getContentPane().add(btnSistemanSartu);
		/**
		textArea = new JTextArea();
		textArea.setBounds(94, 199, 290, 55);
		getContentPane().add(textArea);
		**/
		pwdPassword = new JPasswordField();
		pwdPassword.setText("password");
		pwdPassword.setBounds(110, 101, 180, 20);
		getContentPane().add(pwdPassword);
		
		rdbtnGidaria= new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Driver"));
		buttonGroup1.add(rdbtnGidaria);
		rdbtnGidaria.setBounds(120, 120, 85, 20);
		//getContentPane().add(rdbtnGidaria);
		
		
		rdbtnBidaiaria = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Traveler"));
		buttonGroup1.add(rdbtnBidaiaria);
		rdbtnBidaiaria.setBounds(205, 120, 80, 20); 
		//getContentPane().add(rdbtnBidaiaria);
		
		ImageIcon loginIcon= new ImageIcon("./ikonoak/loginImg.png");
		Image loginIconEskalatuta = loginIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		JLabel label=new JLabel(new ImageIcon(loginIconEskalatuta));
		label.setBounds(175,10,50,50);
		getContentPane().add(label);
		
		jButtonBack.setBounds(157,186,80,30);
		this.getContentPane().add(jButtonBack,null);
		jButtonBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				HasierakoGUI a=new HasierakoGUI();
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
		/**
		rdbtnNewRadioButton = new JRadioButton("English", true);
		rdbtnNewRadioButton.setBounds(245,230,80,20);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();				}
		});
		buttonGroup2.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton_1.setBounds(90,230,72,20);
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();				}
		});
		buttonGroup2.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.setBounds(160,230,85,20);
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
			}
		});
		buttonGroup2.add(rdbtnNewRadioButton_2);
		getContentPane().add(rdbtnNewRadioButton_1);
		getContentPane().add(rdbtnNewRadioButton_2);
		getContentPane().add(rdbtnNewRadioButton);
		*/
	}
	
}

