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
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import domain.Balorazio;
import domain.Book;
import domain.Driver;
import domain.Ride;
import domain.Traveler;

public class RideReviewsGUI extends JFrame {
	private static final long serialVersionUID=1L;
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
	private JButton jButtonShowReviews=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyReviewsGUI.showReviews"));
	
	private JTable tableRides2= new JTable();
	
	private JLabel lblReviews;
	
	private DefaultTableModel tableModelRides2;
	private String[] columnNamesRides2;
	private JScrollPane scrollPaneEvents2 = new JScrollPane();
	
	public RideReviewsGUI (Driver d, Ride r) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 409));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.myRides")+" > "+ResourceBundle.getBundle("Etiquetas").getString("RideReviewsGUI.rideReview"));
		setResizable(false);
		columnNamesRides2= new String[] {
				ResourceBundle.getBundle("Etiquetas").getString("MyRideGUI.Traveler"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.rating"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.description"),
		};
		jButtonBack.setBounds(206,329,80,30);
		this.getContentPane().add(jButtonBack,null);
		jButtonBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				MyRidesGUI a=new MyRidesGUI(d);
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
		scrollPaneEvents2.setBounds(new Rectangle(77, 127, 346, 150));
		scrollPaneEvents2.setViewportView(tableRides2);
		tableModelRides2 = new DefaultTableModel(null, columnNamesRides2);

		tableRides2.setModel(tableModelRides2);

		tableModelRides2.setColumnIdentifiers(columnNamesRides2);
		tableModelRides2.setColumnCount(4); // another column added to allocate ride objects
		
		tableRides2.getColumnModel().removeColumn(tableRides2.getColumnModel().getColumn(3)); // not shown in JTable
		this.getContentPane().add(scrollPaneEvents2, null);
		
		lblReviews = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RideReviewsGUI.rideReview")); //$NON-NLS-1$ //$NON-NLS-2$
		lblReviews.setBounds(77, 102, 74, 14);
		getContentPane().add(lblReviews);
		
		ImageIcon myReviewsIcon= new ImageIcon("./ikonoak/myReviews.png");
		Image myReviewsIconEskalatuta = myReviewsIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		JLabel label=new JLabel(new ImageIcon(myReviewsIconEskalatuta));
		label.setBounds(225,21,50,50);
		getContentPane().add(label);
		
		List<Balorazio> balorazioak=HasierakoGUI.getBusinessLogic().getBalorazioakFromRide(r.getRideNumber());
		for (Balorazio b:balorazioak){
			Vector<Object> row = new Vector<Object>();
					//System.out.println(ride.getRideNumber());
			Traveler t=b.getTraveler();
			row.add(t.getIzena());
			row.add(b.getPuntuazioa());
			row.add(b.getKomentarioa());
			row.add(b); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
			tableModelRides2.addRow(row);
		}
	}
}
