package gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import domain.Book;
import domain.Driver;
import domain.Kotxe;
import domain.Traveler;

public class MyCarsGUI extends JFrame{
	private static final long serialVersionUID=1L;
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
	private JButton jButtonShowCars=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyCarsGUI.ShowCars"));

	private JTable tableRides2= new JTable();

	private JLabel lblCars;

	private DefaultTableModel tableModelRides2;
	private String[] columnNamesRides2;
	private JScrollPane scrollPaneEvents2 = new JScrollPane();
	

	
	public MyCarsGUI(Driver d) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 378));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.myCars"));
		setResizable(false);
		columnNamesRides2= new String[] {
				ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.Brand"),
				ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.Model"),
				ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.NPlaces"),
				ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.PlateNumber")
		};
		jButtonBack.setBounds(207,278,80,30);
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
		
		scrollPaneEvents2.setBounds(new Rectangle(77, 76, 346, 150));
		scrollPaneEvents2.setViewportView(tableRides2);
		tableModelRides2 = new DefaultTableModel(null, columnNamesRides2);

		tableRides2.setModel(tableModelRides2);

		tableModelRides2.setColumnIdentifiers(columnNamesRides2);
		tableModelRides2.setColumnCount(5); // another column added to allocate ride objects

		//tableRides2.getColumnModel().getColumn(0).setPreferredWidth(140);
		//tableRides2.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableRides2.getColumnModel().removeColumn(tableRides2.getColumnModel().getColumn(4)); // not shown in JTable
		this.getContentPane().add(scrollPaneEvents2, null);
		
		lblCars = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.myCars")); //$NON-NLS-1$ //$NON-NLS-2$
		lblCars.setBounds(77, 56, 74, 14);
		getContentPane().add(lblCars);
		
		jButtonShowCars.setBounds(171,237,151,30);
		this.getContentPane().add(jButtonShowCars,null);
		jButtonShowCars.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				List<Kotxe> kotxeak=HasierakoGUI.getBusinessLogic().getCars(d.getNAN());
				for (Kotxe car:kotxeak){
					Vector<Object> row = new Vector<Object>();
					//System.out.println(ride.getRideNumber());
					row.add(car.getMarka());
					row.add(car.getModeloa());
					row.add(car.getEserlekuKopurua());
					row.add(car.getMatrikula());
					row.add(car); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
					tableModelRides2.addRow(row);
				}
				jButtonShowCars.setEnabled(false);
			}
		});
		
	}
	}



