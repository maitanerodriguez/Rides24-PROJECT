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
import domain.Ride;
import domain.Traveler;
import exceptions.ratingMoreThanFiveException;
import exceptions.reviewAlreadyExistsException;

public class CreateReviewGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton jButtonCreateReview=new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.createReview"));
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
	
	private JLabel lblPuntuazio= new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.rating"));
	private JLabel lblDeskribapena= new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.description"));
	private JLabel errore=new JLabel();
	
	private JTextField fieldPuntuazio=new JTextField();
	private JTextField fieldDeskribapena=new JTextField();
	
	public CreateReviewGUI(Traveler t, Ride r) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(400, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.createReview"));
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
		
		fieldPuntuazio.setBounds(186, 133, 137, 20);
		this.getContentPane().add(fieldPuntuazio);
		fieldPuntuazio.setColumns(10);
		
		fieldDeskribapena.setBounds(186, 164, 137, 20);
		this.getContentPane().add(fieldDeskribapena);
		fieldDeskribapena.setColumns(10);
		
		jButtonCreateReview.setBounds(116,226,144,30);
		this.getContentPane().add(jButtonCreateReview,null);
		
		errore.setBounds(69, 195, 317,20);
		errore.setVisible(true);
		getContentPane().add(errore);
		
		lblPuntuazio.setBounds(69, 133, 167,20);
		lblPuntuazio.setVisible(true);
		getContentPane().add(lblPuntuazio);
		
		lblDeskribapena.setBounds(69, 163, 167,20);
		lblDeskribapena.setVisible(true);
		getContentPane().add(lblDeskribapena);
		
		ImageIcon createReviewIcon= new ImageIcon("./ikonoak/createReview.png");
		Image createReviewIconEskalatuta = createReviewIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		JLabel label=new JLabel(new ImageIcon(createReviewIconEskalatuta));
		label.setBounds(174,34,50,50);
		getContentPane().add(label);
		
		jButtonCreateReview.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				errore.setForeground(Color.BLACK);
				errore.setText("");
				String puntuazioa=fieldPuntuazio.getText().trim();				
				String deskribapena=fieldDeskribapena.getText().trim();
				if (puntuazioa.isEmpty()||deskribapena.isEmpty()) {
					errore.setForeground(Color.RED);
					errore.setBounds(80, 195, 317,20);
		            errore.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.fillInformation"));
		            return;
				}
				int puntuazio;
				try {
					puntuazio = Integer.parseInt(puntuazioa);
				} catch (NumberFormatException e1) {
				    errore.setForeground(Color.RED);
				    errore.setBounds(100, 195, 317,20);
				    errore.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.correctRating"));
				    return;
				}
				puntuazio = Integer.parseInt(puntuazioa);
				if(puntuazio<0) {
					errore.setBounds(116, 195, 317,20);
					errore.setForeground(Color.RED);
				    errore.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.ratingNegative"));
				    return;
				}
				LocalDateTime dataOrdua=LocalDateTime.now();
			    DateTimeFormatter formatoa=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			    String dataOrduaFormateatuta=dataOrdua.format(formatoa);
				try {
					BLFacade facade=HasierakoGUI.getBusinessLogic();
					Balorazio balorazio=facade.createBalorazio(1, puntuazio, deskribapena,dataOrduaFormateatuta,t.getNAN(),r.getRideNumber());
					errore.setBounds(116, 195, 317,20);
					errore.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.ratingCreated"));
				}catch(reviewAlreadyExistsException | ratingMoreThanFiveException e1) {
					errore.setBounds(69, 195, 317,20);
					errore.setForeground(Color.RED);
			    	errore.setText(e1.getMessage());
				}
			}
		});
	}
}
