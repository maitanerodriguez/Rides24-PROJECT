package gui;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Traveler;
import domain.User;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.persistence.RollbackException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

public class RegisterGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField email = new JTextField();
	private JTextField izena = new JTextField();

	private JRadioButton bidaiariBotoia = new JRadioButton(
			ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Bidaiaria"));
	private JRadioButton gidariBotoia = new JRadioButton(
			ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Gidaria"));

	private JLabel Emaila = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Email"));
	private JLabel errore=new JLabel();
	private JLabel Pasahitza = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Pasahitza"));
	private JLabel Izena = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Izena"));

	private JButton erregistratuBotoia = new JButton(
			ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Erregistratu"));
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPasswordField passwordField;
	private JTextField a;
	private JTextField JD;
	private JTextField N;
	private JTextField TZ;
	private JTextField EI;
	private JRadioButton femaleButton;
	private JRadioButton maleButton;
	private ButtonGroup sexButtonGroup;
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));

	/**
	 * Create the frame.
	 */
	public RegisterGUI() {


		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(400, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Register"));
		setResizable(false);//ez zaigu interesatzen erabiltzaileak pantaila osoan jartzea, hortaz komando hau erabiltzen dugu hori ekiditeko (ez dakigu ondo dagoen ala ez)

		email.setBounds(208, 89, 137, 20);
		this.getContentPane().add(email);
		email.setColumns(10);

		izena.setBounds(208, 139, 137, 20);
		getContentPane().add(izena);
		izena.setColumns(10);

		bidaiariBotoia.setBounds(86, 343, 109, 23);
		getContentPane().add(bidaiariBotoia);
		buttonGroup.add(bidaiariBotoia);
		/*
		bidaiariBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				erregistratuBotoia.setEnabled(true);
			}
		});
		*/

		gidariBotoia.setBounds(208, 343, 109, 23);
		getContentPane().add(gidariBotoia);
		buttonGroup.add(gidariBotoia);
		/**
		gidariBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				erregistratuBotoia.setEnabled(true);
			}
		});
		*/

		Emaila.setBounds(42, 89, 153, 14);
		getContentPane().add(Emaila);

		Pasahitza.setBounds(42, 114, 153, 14);
		getContentPane().add(Pasahitza);

		Izena.setBounds(42, 139, 153, 14);
		getContentPane().add(Izena);

		erregistratuBotoia.setBounds(42, 373, 303, 30);
		getContentPane().add(erregistratuBotoia);
		//erregistratuBotoia.setEnabled(false);
		erregistratuBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		
		
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(208, 114, 137, 20);
		getContentPane().add(passwordField);
		
		JLabel Abizena = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Abizena")); //$NON-NLS-1$ //$NON-NLS-2$
		Abizena.setBounds(42, 164, 153, 14);
		getContentPane().add(Abizena);
		
		a = new JTextField();
		a.setColumns(10);
		a.setBounds(208, 164, 137, 20);
		getContentPane().add(a);
		
		JLabel JaiotzeData = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.JaiotzeData")); //$NON-NLS-1$ //$NON-NLS-2$
		JaiotzeData.setBounds(42, 189, 153, 14);
		getContentPane().add(JaiotzeData);
		
		JD = new JTextField();
		JD.setColumns(10);
		JD.setBounds(208, 189, 137, 20);
		getContentPane().add(JD);
		
		JLabel NAN = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.NAN")); //$NON-NLS-1$ //$NON-NLS-2$
		NAN.setBounds(42, 214, 153, 14);
		getContentPane().add(NAN);
		
		N = new JTextField();
		N.setColumns(10);
		N.setBounds(208, 214, 137, 20);
		getContentPane().add(N);
		
		JLabel telefonoZenbakia = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.telefonoZenbakia")); //$NON-NLS-1$ //$NON-NLS-2$
		telefonoZenbakia.setBounds(42, 239, 153, 14);
		getContentPane().add(telefonoZenbakia);
		
		TZ = new JTextField();
		TZ.setColumns(10);
		TZ.setBounds(208, 239, 137, 20);
		getContentPane().add(TZ);
		
		/**
		JLabel bankuKontua = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.bankuKontua")); //$NON-NLS-1$ //$NON-NLS-2$
		bankuKontua.setBounds(63, 237, 153, 14);
		contentPane.add(bankuKontua);
		
		BK = new JTextField();
		BK.setColumns(10);
		BK.setBounds(226, 231, 137, 20);
		contentPane.add(BK);
		*/
		
		JLabel Erabiltzaile = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Erabiltzaile")); //$NON-NLS-1$ //$NON-NLS-2$
		Erabiltzaile.setBounds(42, 264, 153, 14);
		getContentPane().add(Erabiltzaile);
		
		EI = new JTextField();
		EI.setColumns(10);
		EI.setBounds(208, 264, 137, 20);
		getContentPane().add(EI);
		
		JLabel Sexua = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Sexua")); //$NON-NLS-1$ //$NON-NLS-2$
		Sexua.setBounds(42, 289, 153, 14);
		getContentPane().add(Sexua);
		
		errore.setBounds(42, 316, 300,20);
		errore.setVisible(true);
		getContentPane().add(errore);
		
		femaleButton = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Female"));
		maleButton = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Male"));
		sexButtonGroup=new ButtonGroup();
		sexButtonGroup.add(femaleButton);
		sexButtonGroup.add(maleButton);
		femaleButton.setBounds(200, 291, 90, 20);
		maleButton.setBounds(292, 291, 70, 20);
		getContentPane().add(femaleButton);
		getContentPane().add(maleButton);
		/**
		S = new JTextField();
		S.setColumns(10);
		S.setBounds(208, 289, 137, 20);
		getContentPane().add(S);
		*/
		jButtonBack.setBounds(152,414,80,30);
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
		erregistratuBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				errore.setText(""); 

		        String mota = gidariBotoia.isSelected() ? "Gidari" : "Bidaiari";

		        String izenaa = izena.getText().trim();
		        String abizena = a.getText().trim();
		        String emaila = email.getText().trim();
		        String jaiotzeData = JD.getText().trim();
		        String telefonoInput = TZ.getText().trim();
		        String NAN = N.getText().trim();
		        String erabiltzaileIzena = EI.getText().trim();
		        String sexua = femaleButton.isSelected() ? "F" : "M";
		        String pasahitza = String.valueOf(passwordField.getPassword()).trim();

		  
		        if (izenaa.isEmpty() || abizena.isEmpty() || emaila.isEmpty() || jaiotzeData.isEmpty() ||
		            telefonoInput.isEmpty() || NAN.isEmpty() || erabiltzaileIzena.isEmpty() || 
		            !femaleButton.isSelected() && !maleButton.isSelected()|| pasahitza.isEmpty()) {
		        	errore.setForeground(Color.RED);
		            errore.setText("Informazio guztia bete mesedez");
		            return;
		        }
		        if (!emaila.contains("@") || emaila.indexOf("@") == 0 || emaila.indexOf("@") == emaila.length() - 1) {
		            errore.setForeground(Color.RED);  
		            errore.setText("Emailak behar du '@' karakterea");
		            return;
		        }
		        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		        sdf.setLenient(false); 
		        Date jaiotzeDataFormatua;
		        try {
		            jaiotzeDataFormatua = sdf.parse(jaiotzeData);
		            jaiotzeData = sdf.format(jaiotzeDataFormatua); 
		        } catch (ParseException e) {
		            errore.setForeground(Color.RED);
		            errore.setText("Jaiotze datarako formatua: dd/MM/yyyy");
		            return;
		        }
		       
		        int telefonoZenbakia;
		        try {
		            telefonoZenbakia = Integer.parseInt(telefonoInput);
		        } catch (NumberFormatException e) {
		        	errore.setForeground(new Color(255,0,0));
		            errore.setText("Telefono zenbakia ez da baliozko zenbaki bat");
		            return;
		        }

		      
		        if (!HasierakoGUI.getBusinessLogic().existitu(NAN,emaila, erabiltzaileIzena, telefonoZenbakia )) {
		            HasierakoGUI.getBusinessLogic().register(mota, NAN, erabiltzaileIzena, pasahitza, emaila, izenaa, abizena, jaiotzeData, telefonoZenbakia, sexua);
		            errore.setForeground(Color.BLACK);
		            errore.setText("Erabiltzailea ondo erregistratuta");
		        } else {
		        	errore.setForeground(new Color(255,0,0));
		            errore.setText("NAN, email, log edo telefono zenbakia existitzen dira");
		        }
		    }
			
		});
		ImageIcon loginIcon= new ImageIcon("./ikonoak/registerImg.png");
		Image loginIconEskalatuta = loginIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		JLabel label=new JLabel(new ImageIcon(loginIconEskalatuta));
		label.setBounds(175,10,50,50);
		getContentPane().add(label);
	}
	/**
	private void erregistratu(ActionEvent e) {
		try {
			BLFacade facade = MainGUI.getBusinessLogic();
			String izena = iz.getText();
			String abizena = a.getText();
			String emaila = Emaila.getText();
			Date jaiotzeData = JD.getText();
			Integer telefonoZenbakia = TZ.getText();
			String NAN = N.getText();
			String bankuKontua = BK.getText();
			String erabiltzaileIzena = EI.getText();
			Character sexua = S.getText();
			String pasahitza = String.valueOf(pasahitza.getPassword());

			boolean gidari = gidariBotoia.isSelected();
			if (izena != null && emaila != null && pasahitza != null) {
					if (gidari) {
						//GIDARIA SORTU
					} else {
						// BIDAIARIA SORTU
					}
			}
		
		} catch (Exception e1) {
			e1.getMessage();
		}
	}
	*/
}
