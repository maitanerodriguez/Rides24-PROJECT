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
import domain.Erreklamazio;
import domain.Ride;
import domain.Traveler;

public class ReportsReceivedTGUI extends JFrame {
	private static final long serialVersionUID=1L;
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
	private JButton jButtonShowReports=new JButton (ResourceBundle.getBundle("Etiquetas").getString("ReportsReceivedTGUI.showReports"));
	
	private JTable tableRides2= new JTable();
	
	private JLabel lblReports;
	
	private DefaultTableModel tableModelRides2;
	private String[] columnNamesRides2;
	private JScrollPane scrollPaneEvents2 = new JScrollPane();
	
	public ReportsReceivedTGUI (Traveler t) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 409));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.reportsReceived"));
		setResizable(false);
		columnNamesRides2= new String[] {
				ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.LeavingFrom"),
				ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.GoingTo"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.description"),
				ResourceBundle.getBundle("Etiquetas").getString("Book.state")
		};
		jButtonBack.setBounds(206,329,80,30);
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
		scrollPaneEvents2.setBounds(new Rectangle(77, 127, 346, 150));
		scrollPaneEvents2.setViewportView(tableRides2);
		tableModelRides2 = new DefaultTableModel(null, columnNamesRides2);

		tableRides2.setModel(tableModelRides2);

		tableModelRides2.setColumnIdentifiers(columnNamesRides2);
		tableModelRides2.setColumnCount(5); // another column added to allocate ride objects
		
		tableRides2.getColumnModel().removeColumn(tableRides2.getColumnModel().getColumn(4)); // not shown in JTable
		this.getContentPane().add(scrollPaneEvents2, null);
		
		lblReports = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.reportsReceived")); //$NON-NLS-1$ //$NON-NLS-2$
		lblReports.setBounds(77, 102, 346, 14);
		getContentPane().add(lblReports);
		
		ImageIcon reportsReceivedIcon= new ImageIcon("./ikonoak/reportReceived.png");
		Image reportsReceivedIconEskalatuta = reportsReceivedIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		JLabel label=new JLabel(new ImageIcon(reportsReceivedIconEskalatuta));
		label.setBounds(225,21,50,50);
		getContentPane().add(label);
		
		jButtonShowReports.setBounds(143,288,213,30);
		this.getContentPane().add(jButtonShowReports,null);
		jButtonShowReports.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				List<Erreklamazio> erreklamazioak=HasierakoGUI.getBusinessLogic().getReceivedReports(t.getNAN());
				for (Erreklamazio er:erreklamazioak){
					Vector<Object> row = new Vector<Object>();
					//System.out.println(ride.getRideNumber());
					Ride r=er.getRide();
					row.add(r.getFrom());
					row.add(r.getTo());
					row.add(er.getDeskribapena());
					row.add(er.getEgoera());
					row.add(er); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
					tableModelRides2.addRow(row);
				}
				jButtonShowReports.setEnabled(false);
			}
		});
	}
}
