package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Locale;
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
import exceptions.rideIsNotConfirmed;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyBooksGUI extends JFrame{
	private static final long serialVersionUID=1L;
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back"));
	private JButton jButtonShowBooks=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.ShowBooks"));
	private JButton jButtonConfirmBook=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyBooksGUI.confirmBook"));
	private JButton jButtonReviewRide=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyBooksGUI.reviewRide"));
	private JButton jButtonReport=new JButton (ResourceBundle.getBundle("Etiquetas").getString("MyBooksGUI.report"));
	
	private JTable tableRides2= new JTable();

	private JLabel lblErreserbak;
	
	private JLabel errore=new JLabel();
	
	private DefaultTableModel tableModelRides2;
	private String[] columnNamesRides2;
	private JScrollPane scrollPaneEvents2 = new JScrollPane();
	

	
	public MyBooksGUI(Traveler t) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 409));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.MainTitle")+" > "+ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.myBooks"));
		setResizable(false);
		columnNamesRides2= new String[] {
				ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.LeavingFrom"),
				ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.GoingTo"),
				ResourceBundle.getBundle("Etiquetas").getString("HasierakoGUI.NPlaces"),
				ResourceBundle.getBundle("Etiquetas").getString("Book.state"),
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
		
		errore.setBounds(158, 268, 198,20);
		errore.setVisible(true);
		getContentPane().add(errore);
		
		scrollPaneEvents2.setBounds(new Rectangle(77, 66, 346, 150));
		scrollPaneEvents2.setViewportView(tableRides2);
		tableModelRides2 = new DefaultTableModel(null, columnNamesRides2);

		tableRides2.setModel(tableModelRides2);

		tableModelRides2.setColumnIdentifiers(columnNamesRides2);
		tableModelRides2.setColumnCount(5); // another column added to allocate ride objects

		//tableRides2.getColumnModel().getColumn(0).setPreferredWidth(140);
		//tableRides2.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableRides2.getColumnModel().removeColumn(tableRides2.getColumnModel().getColumn(4)); // not shown in JTable
		this.getContentPane().add(scrollPaneEvents2, null);
		
		lblErreserbak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.books")); //$NON-NLS-1$ //$NON-NLS-2$
		lblErreserbak.setBounds(77, 41, 74, 14);
		getContentPane().add(lblErreserbak);
		
		jButtonShowBooks.setBounds(170,288,151,30);
		this.getContentPane().add(jButtonShowBooks,null);
		jButtonShowBooks.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				List<Book> books=HasierakoGUI.getBusinessLogic().getBooks(t.getNAN());
				for (Book book:books){
					if(book.getEgoeraB()==false) {
						book.setEgoera(ResourceBundle.getBundle("Etiquetas").getString("Book.notCompleted"));
					}else {
						book.setEgoera(ResourceBundle.getBundle("Etiquetas").getString("Book.completed"));
					}
					Vector<Object> row = new Vector<Object>();
					//System.out.println(ride.getRideNumber());
					row.add(book.getRide().getFrom());
					row.add(book.getRide().getTo());
					row.add(book.getEserlekuKop());
					row.add(book.getEgoera());
					row.add(book); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
					tableModelRides2.addRow(row);
				}
				jButtonShowBooks.setEnabled(false);
			}
		});
		jButtonConfirmBook.setBounds(42,227,128,30);
		jButtonConfirmBook.setEnabled(false);
		this.getContentPane().add(jButtonConfirmBook,null);
		jButtonConfirmBook.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				errore.setText("");
				int selectedRow=tableRides2.getSelectedRow();
				if (selectedRow != -1) {
					Book b=(Book) tableModelRides2.getValueAt(selectedRow,4);
					Ride r=b.getRide();
					try {
						HasierakoGUI.getBusinessLogic().confirmBook(b.getBookNumber(), r.getDriver().getNAN());
						//texto para decir que el viaje se ha completado de manera exitosa
					} catch (rideIsNotConfirmed e1) {
						// TODO Auto-generated catch block
						errore.setForeground(Color.RED);
				    	errore.setText(e1.getMessage());
					}
				}
				jButtonConfirmBook.setEnabled(false);
				int rowCount = tableModelRides2.getRowCount();
	            for (int i = rowCount - 1; i >= 0; i--) {
	                tableModelRides2.removeRow(i); // Eliminar la fila desde la última hasta la primera
	            }
	            List<Book> books = HasierakoGUI.getBusinessLogic().getBooks(t.getNAN());
	            
	            // Agregar las filas nuevas a la tabla
	            for (Book book : books) {
	            	if(book.getEgoeraB()==false) {
						book.setEgoera(ResourceBundle.getBundle("Etiquetas").getString("Book.notCompleted"));
					}else {
						book.setEgoera(ResourceBundle.getBundle("Etiquetas").getString("Book.completed"));
					}
	                Vector<Object> row = new Vector<Object>();
	                row.add(book.getRide().getFrom());
	                row.add(book.getRide().getTo());
	                row.add(book.getEserlekuKop());
	                row.add(book.getEgoera());
	                row.add(book); // Agregar el objeto Book para acceso posterior
	                tableModelRides2.addRow(row); // Añadir la fila a la tabla
	            }
			}
		});
		jButtonReviewRide.setBounds(180,227,128,30);
		jButtonReviewRide.setEnabled(false);
		this.getContentPane().add(jButtonReviewRide,null);
		
		jButtonReviewRide.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int selectedRow=tableRides2.getSelectedRow();
		    	if(selectedRow!=-1) {
		    		Book b=(Book) tableModelRides2.getValueAt(selectedRow,4);
		    		Ride r=b.getRide();
		    		CreateReviewGUI a=new CreateReviewGUI(t,r);
					a.setVisible(true);
					a.setLocationRelativeTo(null);
					dispose();
		    	}
			}
		});
		jButtonReport.setBounds(318,227,128,30);
		jButtonReport.setEnabled(false);
		this.getContentPane().add(jButtonReport,null);
		
		jButtonReport.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int selectedRow=tableRides2.getSelectedRow();
		    	if(selectedRow!=-1) {
		    		Book b=(Book) tableModelRides2.getValueAt(selectedRow,4);
		    		Ride r=b.getRide();
		    		CreateReportsGUI a=new CreateReportsGUI(t,r);
					a.setVisible(true);
					a.setLocationRelativeTo(null);
					dispose();

		    	}
			}
		});
		
		tableRides2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		    	int selectedRow=tableRides2.getSelectedRow();
		    	if(selectedRow!=-1) {
		    		Book b=(Book) tableModelRides2.getValueAt(selectedRow,4);
		    		Ride r=b.getRide();
					if (selectedRow != -1&& b.getEgoera().equals(ResourceBundle.getBundle("Etiquetas").getString("Book.notCompleted"))/*&&r.isEginda()==true*/) {
						jButtonConfirmBook.setEnabled(true);
						jButtonReviewRide.setEnabled(false);
						jButtonReport.setEnabled(false);
					}else {
						jButtonConfirmBook.setEnabled(false);
						jButtonReviewRide.setEnabled(true);
						jButtonReport.setEnabled(true);
					}
		    	}
		    }
		});
		
	}
	}


