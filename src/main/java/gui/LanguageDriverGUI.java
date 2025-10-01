package gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import domain.Driver;

public class LanguageDriverGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
	private JRadioButton rdbtnNewRadioButton;
	private static boolean isMarkedEus;
	private static boolean isMarkedEs;
	private static boolean isMarkedEng=true;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public LanguageDriverGUI(Driver d) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(350, 250));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Language"));
		setResizable(false);
		jButtonBack.setBounds(129,170,80,30);
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
		
		
		
		rdbtnNewRadioButton = new JRadioButton("English");
		rdbtnNewRadioButton.setBounds(227,106,80,20);
		rdbtnNewRadioButton.setSelected(isMarkedEng);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isMarkedEus=false;
				isMarkedEs=false;
				isMarkedEng=true;	
				Locale.setDefault(new Locale("en"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
				
								}
		});
		buttonGroup.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton_1.setBounds(33,106,72,20);
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isMarkedEus=true;
				isMarkedEs=false;
				isMarkedEng=false;	
				Locale.setDefault(new Locale("eus"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
								}
		});
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.setBounds(129,106,85,20);
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isMarkedEus=false;
				isMarkedEs=true;
				isMarkedEng=false;	
				Locale.setDefault(new Locale("es"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
				
			}
		});
		
		if(isMarkedEus) {
			rdbtnNewRadioButton_1.setSelected(true);
		}else if(isMarkedEs) {
			rdbtnNewRadioButton_2.setSelected(true);
		}else if(isMarkedEng) {
			rdbtnNewRadioButton.setSelected(true);
		}
		
		buttonGroup.add(rdbtnNewRadioButton_2);
		getContentPane().add(rdbtnNewRadioButton_1);
		getContentPane().add(rdbtnNewRadioButton_2);
		getContentPane().add(rdbtnNewRadioButton);
		
		ImageIcon hizkuntzaIcon= new ImageIcon("./ikonoak/hizkuntza.png");
		Image hizkuntzaIconEskalatuta = hizkuntzaIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		JLabel label=new JLabel(new ImageIcon(hizkuntzaIconEskalatuta));
		label.setBounds(143,30,50,50);
		getContentPane().add(label);
	}
	private void paintAgain() {
		jButtonBack.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Language"));
	}
}

