package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import domain.Traveler;
import exceptions.MoneyDatabaseException;

public class DiruaGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
	private JButton jButtonSet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DiruaGUI.sartuDirua"));
	//private final JLabel lblMoney = new JLabel();
	private JTextField diruaField;
	private JLabel lblmsg=new JLabel();
	public DiruaGUI(Traveler t) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(400, 300));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("DiruaGUI.sartuDirua"));
		setResizable(false);
		jButtonBack.setBounds(160,200,80,30);
		this.getContentPane().add(jButtonBack,null);
		jButtonBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				TravelerGUI a=new TravelerGUI(t);
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
		
		lblmsg.setBounds(128,139,180,20 );
		getContentPane().add(lblmsg);
		jButtonSet.setBounds(128,159,137,30);
		this.getContentPane().add(jButtonSet,null);
		jButtonSet.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				lblmsg.setForeground(Color.BLACK);
				lblmsg.setText("");
				try {
					int diruBerria=Integer.parseInt(diruaField.getText());
					HasierakoGUI.getBusinessLogic().diruaSartu(diruBerria, t.getNAN());
					lblmsg.setText(ResourceBundle.getBundle("Etiquetas").getString("DiruaGUI.MoneySet"));
				}catch(NumberFormatException e1) {
					lblmsg.setForeground(Color.RED);
					lblmsg.setText(ResourceBundle.getBundle("Etiquetas").getString("DiruaGUI.NumberFormatExcpetion"));
				}
				System.out.println(t.getDirua());
			}
		});
		//lblMoney.setBounds(100,100,300,20);
		//getContentPane().add(lblMoney);
		
		diruaField = new JTextField(); //$NON-NLS-1$ //$NON-NLS-2$
		diruaField.setBounds(154, 108, 93, 20);
		getContentPane().add(diruaField);
		diruaField.setColumns(10);
		
		ImageIcon diruaIcon= new ImageIcon("./ikonoak/dirua.png");
		Image diruaIconEskalatuta = diruaIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		JLabel label=new JLabel(new ImageIcon(diruaIconEskalatuta));
		label.setBounds(175,25,50,50);
		getContentPane().add(label);
		
		
	}
}
