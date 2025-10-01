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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import domain.Book;
import domain.Driver;
import domain.Ride;
import domain.Traveler;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyRidesGUI extends JFrame{
	private static final long serialVersionUID=1L;
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
	private JButton jButtonShowRides=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.ShowMyRides"));
	private JButton jButtonShowBooks=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.ShowBooks"));
	private JButton jButtonKantzelatu=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.cancel"));
	private JButton jButtonDeuseztatu=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.Deny"));
	private JButton jButtonEginda=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.Done"));
	private JButton jButtonReviews=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.reviews"));
	private JButton jButtonReports=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.reports"));
	private JButton jButtonReport=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyBooksGUI.report"));
	private JTable tableRides= new JTable();
	private JTable tableRides2= new JTable();
	private JLabel lblNireBidaiak;
	private JLabel lblErreserbak;
	private DefaultTableModel tableModelRides;
	private String[] columnNamesRides;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	
	private DefaultTableModel tableModelRides2;
	private String[] columnNamesRides2;
	private JScrollPane scrollPaneEvents2 = new JScrollPane();
	
	public MyRidesGUI(Driver d) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 711));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.myRides"));
		setResizable(false);
		columnNamesRides= new String[] {
				ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.LeavingFrom"),
				ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.GoingTo"),
				ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.NPlaces"), 
				ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.Price"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.car")
		};
		columnNamesRides2= new String[] {
				ResourceBundle.getBundle("Etiquetas").getString("MyRideGUI.Traveler"), 
				ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.NPlaces"), 
		};
		jButtonBack.setBounds(210,631,80,30);
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
		scrollPaneEvents.setBounds(new Rectangle(77, 75, 346, 150));
		scrollPaneEvents.setViewportView(tableRides);
		tableModelRides = new DefaultTableModel(null, columnNamesRides);

		tableRides.setModel(tableModelRides);

		tableModelRides.setColumnIdentifiers(columnNamesRides);
		tableModelRides.setColumnCount(6); // another column added to allocate ride objects

		//tableRides.getColumnModel().getColumn(0).setPreferredWidth(140);
		//tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
		//tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
		tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(5)); // not shown in JTable
		this.getContentPane().add(scrollPaneEvents, null);
		
		scrollPaneEvents2.setBounds(new Rectangle(77, 388, 346, 150));
		scrollPaneEvents2.setViewportView(tableRides2);
		tableModelRides2 = new DefaultTableModel(null, columnNamesRides2);

		tableRides2.setModel(tableModelRides2);

		tableModelRides2.setColumnIdentifiers(columnNamesRides2);
		tableModelRides2.setColumnCount(3); // another column added to allocate ride objects

		//tableRides2.getColumnModel().getColumn(0).setPreferredWidth(140);
		//tableRides2.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableRides2.getColumnModel().removeColumn(tableRides2.getColumnModel().getColumn(2)); // not shown in JTable
		this.getContentPane().add(scrollPaneEvents2, null);
		
		lblNireBidaiak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.myRides")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNireBidaiak.setBounds(77, 50, 74, 14);
		getContentPane().add(lblNireBidaiak);
		
		lblErreserbak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.books")); //$NON-NLS-1$ //$NON-NLS-2$
		lblErreserbak.setBounds(77, 363, 74, 14);
		getContentPane().add(lblErreserbak);
		
		jButtonShowRides.setBounds(176,318,140,30);
		this.getContentPane().add(jButtonShowRides,null);
		jButtonShowRides.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				List<domain.Ride> rides=HasierakoGUI.getBusinessLogic().getRidesFromDriver(d.getNAN());
				for (Ride ride:rides){
					Vector<Object> row = new Vector<Object>();
					//System.out.println(ride.getRideNumber());
					row.add(ride.getFrom());
					row.add(ride.getTo());
					row.add(ride.getnPlaces());
					row.add(ride.getPrice());
					row.add(ride.getKotxea().getMatrikula());
					row.add(ride); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
					System.out.println(ride.getRideNumber());
					tableModelRides.addRow(row);
				}
				jButtonShowRides.setEnabled(false);
			}
		});
		jButtonShowBooks.setBounds(168,590,161,30);
		this.getContentPane().add(jButtonShowBooks,null);
		jButtonShowBooks.setEnabled(false);
		jButtonShowBooks.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int selectedRow=tableRides.getSelectedRow();
				if (selectedRow != -1) {
					Ride r=(Ride) tableModelRides.getValueAt(selectedRow,5);
					List<Book> books=HasierakoGUI.getBusinessLogic().getBooksFromRide(r.getRideNumber());
					for (Book book:books){
						Vector<Object> row = new Vector<Object>();
						//System.out.println(ride.getRideNumber());
						row.add(book.getTraveler().getIzena());
						row.add(book.getEserlekuKop());
						row.add(book); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
						tableModelRides2.addRow(row);
					}
				}
				jButtonShowBooks.setEnabled(false);
				
			}
		});
		
		tableRides.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		    	tableModelRides2.setColumnCount(3);
		    	tableRides2.setModel(tableModelRides2);
		    	tableRides2.getColumnModel().removeColumn(tableRides2.getColumnModel().getColumn(2));
		    	int selectedRow=tableRides.getSelectedRow();
				if (selectedRow != -1) {
					jButtonShowBooks.setEnabled(true);
					jButtonKantzelatu.setEnabled(true);
					jButtonDeuseztatu.setEnabled(false);
					Ride r=(Ride) tableModelRides.getValueAt(selectedRow,5);
					if(r.isEginda()==false) {
						jButtonEginda.setEnabled(true);
						jButtonKantzelatu.setEnabled(true);
						jButtonReviews.setEnabled(false);
						jButtonReports.setEnabled(false);
					}else {
						jButtonEginda.setEnabled(false);
						jButtonKantzelatu.setEnabled(false);
						jButtonReviews.setEnabled(true);
						jButtonReports.setEnabled(true);
					}
					}
				int rowCount1 = tableModelRides2.getRowCount();
	            for (int i = rowCount1 - 1; i >= 0; i--) {
	                tableModelRides2.removeRow(i); // Eliminar la fila desde la última hasta la primera
	            }
				}
		});
		jButtonReviews.setBounds(263,277,129,30);
		this.getContentPane().add(jButtonReviews,null);
		jButtonReviews.setEnabled(false);
		jButtonReviews.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow=tableRides.getSelectedRow();
				if (selectedRow != -1) {
					Ride r=(Ride) tableModelRides.getValueAt(selectedRow,5);
					RideReviewsGUI a=new RideReviewsGUI(d, r);
					a.setVisible(true);
					a.setLocationRelativeTo(null);
					dispose();
				}
			}
		});
		
		jButtonReports.setBounds(100,277,129,30);
		this.getContentPane().add(jButtonReports,null);
		jButtonReports.setEnabled(false);
		jButtonReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow=tableRides.getSelectedRow();
				if (selectedRow != -1) {
					Ride r=(Ride) tableModelRides.getValueAt(selectedRow,5);
					RideReportsGUI a=new RideReportsGUI(d, r);
					a.setVisible(true);
					a.setLocationRelativeTo(null);
					dispose();
				}
			}
		});
		
		jButtonDeuseztatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow=tableRides2.getSelectedRow();
				if (selectedRow != -1) {
					Book b=(Book) tableModelRides2.getValueAt(selectedRow,2);
					//System.out.println(b.getEserlekuKop());
					Traveler t=b.getTraveler();
					Ride r=b.getRide();
					HasierakoGUI.getBusinessLogic().denyBook(b.getBookNumber(),r.getRideNumber() ,t.getNAN());
					//int n=(int)tableModelRides2.getValueAt(selectedRow,1);
					//System.out.println(n);
					tableModelRides2.setDataVector(null, columnNamesRides2);
					jButtonDeuseztatu.setEnabled(false);
				}
			}
		});
		//jButtonOnartu jButtonDeuseztatu
		
		jButtonKantzelatu.setBounds(263,236,129,30);
		this.getContentPane().add(jButtonKantzelatu,null);
		jButtonKantzelatu.setEnabled(false);
		
		
		jButtonDeuseztatu.setBounds(77,549,100,30);
		this.getContentPane().add(jButtonDeuseztatu,null);
		jButtonDeuseztatu.setEnabled(false);
		
		jButtonReport.setBounds(323,549,100,30);
		this.getContentPane().add(jButtonReport,null);
		jButtonReport.setEnabled(false);
		jButtonReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow=tableRides2.getSelectedRow();
				if (selectedRow != -1) {
					
					Book b=(Book) tableModelRides2.getValueAt(selectedRow,2);
		    		CreateReportsDGUI a=new CreateReportsDGUI(d,b);
					a.setVisible(true);
					a.setLocationRelativeTo(null);
					dispose();
					
				}
			}
		});
		
		tableRides2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		    	int selectedRow=tableRides2.getSelectedRow();
				if (selectedRow != -1) {
					/**
					Book b=(Book) tableModelRides2.getValueAt(selectedRow,2);
					if (selectedRow != -1&& b.getEgoera().equals(ResourceBundle.getBundle("Etiquetas").getString("Book.completed"))) {
						
					}
					*/
					Book b=(Book) tableModelRides2.getValueAt(selectedRow,2);
					Ride r=b.getRide();
					if(r.isEginda()==false) {
						jButtonDeuseztatu.setEnabled(true);
						jButtonReport.setEnabled(false);

						
					}else {
						jButtonDeuseztatu.setEnabled(false);
						jButtonReport.setEnabled(true);

						
					}
					
					}
				} 
		});
		jButtonKantzelatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow=tableRides.getSelectedRow();
				if (selectedRow != -1) {
					Ride r=(Ride) tableModelRides.getValueAt(selectedRow,5);
					//System.out.println(b.getEserlekuKop());
					HasierakoGUI.getBusinessLogic().cancelRide(r.getRideNumber(), d.getNAN());
					//int n=(int)tableModelRides2.getValueAt(selectedRow,1);
					//System.out.println(n)
					int rowCount1 = tableModelRides2.getRowCount();
		            for (int i = rowCount1 - 1; i >= 0; i--) {
		                tableModelRides2.removeRow(i); // Eliminar la fila desde la última hasta la primera
		            }
					List<Book> books=HasierakoGUI.getBusinessLogic().getBooksFromRide(r.getRideNumber());
					if(books!=null)
					{
						for (Book book:books){
							Vector<Object> row = new Vector<Object>();
							//System.out.println(ride.getRideNumber());
							row.add(book.getTraveler().getIzena());
							row.add(book.getEserlekuKop());
							row.add(book); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
							tableModelRides2.addRow(row);
						}
					}
				}
				jButtonKantzelatu.setEnabled(false);
				jButtonEginda.setEnabled(false);
				int rowCount = tableModelRides.getRowCount();
	            for (int i = rowCount - 1; i >= 0; i--) {
	                tableModelRides.removeRow(i); // Eliminar la fila desde la última hasta la primera
	            }
	            List<domain.Ride> rides=HasierakoGUI.getBusinessLogic().getRidesFromDriver(d.getNAN());
				for (Ride ride:rides){
					Vector<Object> row = new Vector<Object>();
					//System.out.println(ride.getRideNumber());
					row.add(ride.getFrom());
					row.add(ride.getTo());
					row.add(ride.getnPlaces());
					row.add(ride.getPrice());
					row.add(ride.getKotxea().getMatrikula());
					row.add(ride); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
					System.out.println(ride.getRideNumber());
					tableModelRides.addRow(row);
				}
				
			}
		});
		jButtonEginda.setBounds(100,236,129,30);
		this.getContentPane().add(jButtonEginda,null);
		jButtonEginda.setEnabled(false);
		jButtonEginda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow=tableRides.getSelectedRow();
				if (selectedRow != -1) {
					Ride r=(Ride) tableModelRides.getValueAt(selectedRow,5);
					//System.out.println(b.getEserlekuKop());
					HasierakoGUI.getBusinessLogic().confirmRide(r.getRideNumber());
					int rowCount1 = tableModelRides2.getRowCount();
		            for (int i = rowCount1 - 1; i >= 0; i--) {
		                tableModelRides2.removeRow(i); // Eliminar la fila desde la última hasta la primera
		            }
				}
				jButtonEginda.setEnabled(false);
				int rowCount = tableModelRides.getRowCount();
	            for (int i = rowCount - 1; i >= 0; i--) {
	                tableModelRides.removeRow(i); // Eliminar la fila desde la última hasta la primera
	            }
	            List<domain.Ride> rides=HasierakoGUI.getBusinessLogic().getRidesFromDriver(d.getNAN());
				for (Ride ride:rides){
					Vector<Object> row = new Vector<Object>();
					//System.out.println(ride.getRideNumber());
					row.add(ride.getFrom());
					row.add(ride.getTo());
					row.add(ride.getnPlaces());
					row.add(ride.getPrice());
					row.add(ride.getKotxea().getMatrikula());
					row.add(ride); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
					System.out.println(ride.getRideNumber());
					tableModelRides.addRow(row);
				}
				jButtonKantzelatu.setEnabled(false);
				
			}
		});
		
	}
	}

