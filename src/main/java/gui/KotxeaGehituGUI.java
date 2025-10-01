package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Kotxe;
import exceptions.bookAlreadyExistException;
import exceptions.carAlreadyExistsException;

public class KotxeaGehituGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
	private JLabel lblMarka= new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.Brand"));
	private JLabel lblModeloa= new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.Model"));
	private JLabel lblEserlekuKop= new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.SeatsNumber"));
	private JLabel lblMatrikula= new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.PlateNumber"));
	private JLabel errore=new JLabel();
	
	private JTextField fieldMarka=new JTextField();
	private JTextField fieldModeloa=new JTextField();
	private JTextField fieldEserlekuKop=new JTextField();
	private JTextField fieldMatrikula=new JTextField();
	
	private JButton jButtonAddCar = new JButton(
			ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.addCar"));
	public KotxeaGehituGUI(Driver d) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(400, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.addCar"));
		setResizable(false);//ez zaigu interesatzen erabiltzaileak pantaila osoan jartzea, hortaz komando hau erabiltzen dugu hori ekiditeko (ez dakigu ondo dagoen ala ez)
		
		jButtonBack.setBounds(146,285,80,30);
		this.getContentPane().add(jButtonBack,null);
		jButtonBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				DriverGUI a=new DriverGUI(d);
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
		
		fieldMarka.setBounds(186, 90, 137, 20);
		this.getContentPane().add(fieldMarka);
		fieldMarka.setColumns(10);
		
		fieldModeloa.setBounds(186, 121, 137, 20);
		this.getContentPane().add(fieldModeloa);
		fieldModeloa.setColumns(10);
		
		fieldEserlekuKop.setBounds(186, 152, 137, 20);
		this.getContentPane().add(fieldEserlekuKop);
		fieldEserlekuKop.setColumns(10);
		
		fieldMatrikula.setBounds(186, 183, 137, 20);
		this.getContentPane().add(fieldMatrikula);
		fieldMatrikula.setColumns(10);
		
		jButtonAddCar.setBounds(115,244,144,30);
		this.getContentPane().add(jButtonAddCar,null);
		
		errore.setBounds(115, 224, 194,20);
		errore.setVisible(true);
		getContentPane().add(errore);
		
		lblMarka.setBounds(69, 90, 167,20);
		lblMarka.setVisible(true);
		getContentPane().add(lblMarka);
		
		lblModeloa.setBounds(69, 121, 167,20);
		lblModeloa.setVisible(true);
		getContentPane().add(lblModeloa);
		
		lblEserlekuKop.setBounds(69, 152, 167,20);
		lblEserlekuKop.setVisible(true);
		getContentPane().add(lblEserlekuKop);
		
		lblMatrikula.setBounds(69, 183, 167,20);
		lblMatrikula.setVisible(true);
		getContentPane().add(lblMatrikula);
		
		ImageIcon kotxeIcon= new ImageIcon("./ikonoak/kotxe.png");
		Image kotxeIconEskalatuta = kotxeIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		JLabel label=new JLabel(new ImageIcon(kotxeIconEskalatuta));
		label.setBounds(175,10,50,50);
		getContentPane().add(label);
		
		jButtonAddCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				errore.setForeground(Color.BLACK);
				errore.setText("");
				String marka=fieldMarka.getText().trim();
				String modeloa=fieldModeloa.getText().trim();
				String eserlekuKop=fieldEserlekuKop.getText().trim();
				String matrikula=fieldMatrikula.getText().trim();
				
				if (marka.isEmpty()||modeloa.isEmpty()||eserlekuKop.isEmpty()||matrikula.isEmpty()) {
					errore.setForeground(Color.RED);
		            errore.setText("Informazio guztia bete mesedez");
		            return;
				}
				int eserlekukopurua;
			    try {
			       eserlekukopurua = Integer.parseInt(eserlekuKop);
			    } catch (NumberFormatException e) {
			       errore.setForeground(Color.RED);
			       errore.setText("Eserleku kopurua ondo sartu mesedez");
			       return;
			    }
			    try {
			    	BLFacade facade=HasierakoGUI.getBusinessLogic();
			    	Kotxe k=facade.createCar(marka, modeloa, eserlekukopurua, matrikula, d.getNAN());
			    	errore.setText(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.carCreated"));
			    } catch(carAlreadyExistsException e1) {
			    	errore.setForeground(Color.RED);
			    	errore.setText(e1.getMessage());
			    	
			    }
			}
		});
	}
}
