package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import businessLogic.BLFacade;
import domain.Balorazio;
import domain.Driver;
import domain.Erreklamazio;
import domain.Ride;
import domain.Traveler;
import exceptions.erreklamazioAlreadyExistsException;
import exceptions.ratingMoreThanFiveException;
import exceptions.reviewAlreadyExistsException;

public class CreateReportsGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton jButtonCreateReport=new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateReportsGUI.createReport"));
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
	
	private JLabel lblDeskribapena= new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.description"));
	private JLabel errore=new JLabel();
	
	private JTextField fieldDeskribapena=new JTextField();
	
	public CreateReportsGUI(Traveler t, Ride r) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(400, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.myBooks")+" > "+ResourceBundle.getBundle("Etiquetas").getString("CreateReportsGUI.createReport"));
		setResizable(false);
		jButtonBack.setBounds(150,267,80,30);
		this.getContentPane().add(jButtonBack,null);
		jButtonBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				MyBooksGUI a=new MyBooksGUI(t);
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
		
		
		fieldDeskribapena.setBounds(186, 164, 137, 20);
		this.getContentPane().add(fieldDeskribapena);
		fieldDeskribapena.setColumns(10);
		
		jButtonCreateReport.setBounds(116,226,144,30);
		this.getContentPane().add(jButtonCreateReport,null);
		
		errore.setBounds(69, 195, 317,20);
		errore.setVisible(true);
		getContentPane().add(errore);
		
		lblDeskribapena.setBounds(69, 163, 167,20);
		lblDeskribapena.setVisible(true);
		getContentPane().add(lblDeskribapena);
		
		ImageIcon createReportIcon= new ImageIcon("./ikonoak/report.png");
		Image createReportIconEskalatuta = createReportIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		JLabel label=new JLabel(new ImageIcon(createReportIconEskalatuta));
		label.setBounds(174,34,50,50);
		getContentPane().add(label);
		
		jButtonCreateReport.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				errore.setForeground(Color.BLACK);
				errore.setText("");			
				String deskribapena=fieldDeskribapena.getText().trim();
				if (deskribapena.isEmpty()) {
					errore.setForeground(Color.RED);
					errore.setBounds(80, 195, 317,20);
		            errore.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.fillInformation"));
		            return;
				}
				LocalDateTime dataOrdua=LocalDateTime.now();
			    DateTimeFormatter formatoa=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			    String dataOrduaFormateatuta=dataOrdua.format(formatoa);
				try {
					BLFacade facade=HasierakoGUI.getBusinessLogic();
					Driver d=r.getDriver();
					Erreklamazio erreklamazio=facade.createErreklamazio(deskribapena, dataOrduaFormateatuta, t.getNan(),d.getNAN(),r.getRideNumber());
					errore.setBounds(116, 195, 317,20);
					errore.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.ratingCreated"));
				}catch(erreklamazioAlreadyExistsException e1) {
					errore.setBounds(69, 195, 317,20);
					errore.setForeground(Color.RED);
			    	errore.setText(e1.getMessage());
				}
			}
		});
	}
}
