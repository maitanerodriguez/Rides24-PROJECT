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
import javax.swing.JPasswordField;

import domain.Driver;
import domain.Traveler;

public class DeleteUserTGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPasswordField pwdPassword;
	private JButton erabiltzaileaEzabatu=new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.deleteUser"));
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
	
	private JLabel errorea=new JLabel();
	
	public DeleteUserTGUI(Traveler t) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(400, 300));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.deleteUser"));
		setResizable(false);
		
		jButtonBack.setBounds(157,186,80,30);
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
		
		erabiltzaileaEzabatu.setBounds(136,145,120,30);
		getContentPane().add(erabiltzaileaEzabatu);
		erabiltzaileaEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				errorea.setText("");
				String pass=new String(pwdPassword.getPassword());
				if(!pass.equals(t.getPassword())) {
					errorea.setForeground(Color.RED);
		            errorea.setText(ResourceBundle.getBundle("Etiquetas").getString("DeleteUserTGUI.incorrectPassword"));
				}else {
					HasierakoGUI.getBusinessLogic().deleteUser(t.getNAN());
					HasierakoGUI a=new HasierakoGUI();
					a.setVisible(true);
					a.setLocationRelativeTo(null);
					dispose();
				}
			}
		});
		
		erabiltzaileaEzabatu.setBounds(136,145,120,30);
		getContentPane().add(erabiltzaileaEzabatu);
		/**
		textArea = new JTextArea();
		textArea.setBounds(94, 199, 290, 55);
		getContentPane().add(textArea);
		**/
		pwdPassword = new JPasswordField();
		pwdPassword.setText("password");
		pwdPassword.setBounds(110, 101, 180, 20);
		getContentPane().add(pwdPassword);
		
		ImageIcon deleteUserIcon= new ImageIcon("./ikonoak/deleteUser.png");
		Image deleteUserIconEskalatuta = deleteUserIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		JLabel label=new JLabel(new ImageIcon(deleteUserIconEskalatuta));
		label.setBounds(171,11,50,50);
		getContentPane().add(label);
		
		errorea.setBounds(110,121,180 ,20 );
		getContentPane().add(errorea);
	}
}
