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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import domain.Admin;
import domain.Balorazio;
import domain.Book;
import domain.Driver;
import domain.Erreklamazio;
import domain.Ride;
import domain.Traveler;
import domain.User;

public class ReportsGUI extends JFrame {
	private static final long serialVersionUID=1L;
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
	private JButton jButtonAccept = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReportsGUI.accept"));
	private JButton jButtonDeny = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReportsGUI.deny"));
	
	
	private JTable tableRides2= new JTable();
	
	private JLabel lblReports;
	
	private DefaultTableModel tableModelRides2;
	private String[] columnNamesRides2;
	private JScrollPane scrollPaneEvents2 = new JScrollPane();
	
	public ReportsGUI (Admin ad) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 409));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.myRides")+" > "+ResourceBundle.getBundle("Etiquetas").getString("RideReportsGUI.rideReports"));
		setResizable(false);
		columnNamesRides2= new String[] {
				ResourceBundle.getBundle("Etiquetas").getString("RideReports.complainant"),
				ResourceBundle.getBundle("Etiquetas").getString("RideReports.reported"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.description"),
				ResourceBundle.getBundle("Etiquetas").getString("Book.state")
		};
		jButtonBack.setBounds(206,329,80,30);
		this.getContentPane().add(jButtonBack,null);
		jButtonBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				AdminGUI a=new AdminGUI(ad);
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
		tableModelRides2.setColumnCount(5); // another column added to allocate ride objects
		
		tableRides2.getColumnModel().removeColumn(tableRides2.getColumnModel().getColumn(4)); // not shown in JTable
		this.getContentPane().add(scrollPaneEvents2, null);
		
		lblReports = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RideReportsGUI.rideReports")); //$NON-NLS-1$ //$NON-NLS-2$
		lblReports.setBounds(77, 102, 74, 14);
		getContentPane().add(lblReports);
		
		ImageIcon myReviewsIcon= new ImageIcon("./ikonoak/reportReceived.png");
		Image myReviewsIconEskalatuta = myReviewsIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		JLabel label=new JLabel(new ImageIcon(myReviewsIconEskalatuta));
		label.setBounds(225,21,50,50);
		getContentPane().add(label);
		
		List<Erreklamazio> erreklamazioak=HasierakoGUI.getBusinessLogic().getReports();
		for (Erreklamazio er:erreklamazioak){
			if(er.getEgoera().equals("pending")||er.getEgoera().equals("pendiente")||er.getEgoera().equals("zain")) {
				Vector<Object> row = new Vector<Object>();
				//System.out.println(ride.getRideNumber());
				User u1=er.getErreklamatzailea();
				User u2=er.getErreklamatua();
				row.add(u1.getIzena());
				row.add(u2.getIzena());
				row.add(er.getDeskribapena());
				row.add(er.getEgoera());
				row.add(er); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
				tableModelRides2.addRow(row);
			}
			
		}
		jButtonAccept.setBounds(77,288,100,30);
		jButtonAccept.setEnabled(false);
		this.getContentPane().add(jButtonAccept,null);
		jButtonAccept.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int selectedRow=tableRides2.getSelectedRow();
				if(selectedRow!=-1) {
					Erreklamazio er=(Erreklamazio) tableModelRides2.getValueAt(selectedRow,4);
					HasierakoGUI.getBusinessLogic().acceptReport(er.getIdErreklamazio());
					erreklamazioak.remove(er);
				}
				int rowCount = tableModelRides2.getRowCount();
				List<Erreklamazio> erreklamazioak=HasierakoGUI.getBusinessLogic().getReports();
	            for (int i = rowCount - 1; i >= 0; i--) {
	                tableModelRides2.removeRow(i); // Eliminar la fila desde la última hasta la primera
	            }
				for (Erreklamazio er:erreklamazioak){
					if(er.getEgoera().equals("pending")||er.getEgoera().equals("pendiente")||er.getEgoera().equals("zain")) {
						Vector<Object> row = new Vector<Object>();
						//System.out.println(ride.getRideNumber());
						User u1=er.getErreklamatzailea();
						User u2=er.getErreklamatua();
						row.add(u1.getIzena());
						row.add(u2.getIzena());
						row.add(er.getDeskribapena());
						row.add(er.getEgoera());
						row.add(er); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
						tableModelRides2.addRow(row);
					}
				}
				jButtonAccept.setEnabled(false);
	    		jButtonDeny.setEnabled(false);
			}
		});
		jButtonDeny.setBounds(323,288,100,30);
		jButtonDeny.setEnabled(false);
		this.getContentPane().add(jButtonDeny,null);
		jButtonDeny.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int selectedRow=tableRides2.getSelectedRow();
				if(selectedRow!=-1) {
					Erreklamazio er=(Erreklamazio) tableModelRides2.getValueAt(selectedRow,4);
					HasierakoGUI.getBusinessLogic().denyReport(er.getIdErreklamazio());
					erreklamazioak.remove(er);
				}
				int rowCount = tableModelRides2.getRowCount();
				List<Erreklamazio> erreklamazioak=HasierakoGUI.getBusinessLogic().getReports();
	            for (int i = rowCount - 1; i >= 0; i--) {
	                tableModelRides2.removeRow(i); // Eliminar la fila desde la última hasta la primera
	            }
				for (Erreklamazio er:erreklamazioak){
					if(er.getEgoera().equals("pending")||er.getEgoera().equals("pendiente")||er.getEgoera().equals("zain")) {
						Vector<Object> row = new Vector<Object>();
						//System.out.println(ride.getRideNumber());
						User u1=er.getErreklamatzailea();
						User u2=er.getErreklamatua();
						row.add(u1.getIzena());
						row.add(u2.getIzena());
						row.add(er.getDeskribapena());
						row.add(er.getEgoera());
						row.add(er); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
						tableModelRides2.addRow(row);
					}
				}
				jButtonAccept.setEnabled(false);
	    		jButtonDeny.setEnabled(false);
			}
		});
		tableRides2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		    	int selectedRow=tableRides2.getSelectedRow();
		    	if(selectedRow!=-1) {
		    		jButtonAccept.setEnabled(true);
		    		jButtonDeny.setEnabled(true);
		    	}
		    }
		});
	}
}