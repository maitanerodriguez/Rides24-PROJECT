package gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import domain.Book;
import domain.Driver;
import domain.Mugimendua;
import domain.Ride;
import domain.Traveler;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyMovementsTravelerGUI extends JFrame{
	private static final long serialVersionUID=1L;
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
	private JButton jButtonShowMovements=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyMovementsDriverGUI.showMovements"));

	private JTable tableRides2= new JTable();

	private JLabel lblErreserbak;

	private DefaultTableModel tableModelRides2;
	private String[] columnNamesRides2;
	private JScrollPane scrollPaneEvents2 = new JScrollPane();
	

	
	public MyMovementsTravelerGUI(Traveler t) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 378));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.myMovements"));
		setResizable(false);
		columnNamesRides2= new String[] {
				ResourceBundle.getBundle("Etiquetas").getString("MyMovementsDriverGUI.date"),
				ResourceBundle.getBundle("Etiquetas").getString("MyMovementsDriverGUI.description"),
				ResourceBundle.getBundle("Etiquetas").getString("MyMovementsDriverGUI.quantity"), 
		};
		jButtonBack.setBounds(208,298,80,30);
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
		
		scrollPaneEvents2.setBounds(new Rectangle(31, 96, 419, 150));
		scrollPaneEvents2.setViewportView(tableRides2);
		tableModelRides2 = new DefaultTableModel(null, columnNamesRides2);

		tableRides2.setModel(tableModelRides2);

		tableModelRides2.setColumnIdentifiers(columnNamesRides2);
		tableModelRides2.setColumnCount(4); // another column added to allocate ride objectsç
		tableRides2.getColumnModel().getColumn(2).setPreferredWidth(2);
		tableRides2.getColumnModel().getColumn(0).setPreferredWidth(35);

		//tableRides2.getColumnModel().getColumn(0).setPreferredWidth(140);
		//tableRides2.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableRides2.getColumnModel().removeColumn(tableRides2.getColumnModel().getColumn(3)); // not shown in JTable
		this.getContentPane().add(scrollPaneEvents2, null);
		
		lblErreserbak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MyMovementsDriverGUI.myMovements")); //$NON-NLS-1$ //$NON-NLS-2$
		lblErreserbak.setBounds(31, 71, 138, 14);
		getContentPane().add(lblErreserbak);
		
		ImageIcon myMovementsIcon= new ImageIcon("./ikonoak/mugimendua.png");
		Image myMovementsIconEskalatuta = myMovementsIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		JLabel label=new JLabel(new ImageIcon(myMovementsIconEskalatuta));
		label.setBounds(225,21,50,50);
		getContentPane().add(label);
		
		jButtonShowMovements.setBounds(145,257,209,30);
		this.getContentPane().add(jButtonShowMovements,null);
		jButtonShowMovements.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				List<Mugimendua> mugimenduak=HasierakoGUI.getBusinessLogic().getMugimenduak(t.getNAN());
				for (Mugimendua mugimendua:mugimenduak){
					Vector<Object> row = new Vector<Object>();
					//System.out.println(ride.getRideNumber());
					row.add(mugimendua.getData());
					row.add(mugimendua.getDeskribapena());
					row.add(mugimendua.getDiruKantitatea());
					row.add(mugimendua); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
					tableModelRides2.addRow(row);
				}
				jButtonShowMovements.setEnabled(false);
				tableRides2.getColumnModel().getColumn(2).setPreferredWidth(2);
				tableRides2.getColumnModel().getColumn(0).setPreferredWidth(35);
			}
		});
		
	}
	}

