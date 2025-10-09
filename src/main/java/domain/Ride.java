package domain;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Ride implements Serializable {
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer rideNumber;
	private String from;
	private String to;
	private int nPlaces;
	private Date date;
	private float price;
	private boolean eginda;
	private String whenCreated;
	
	@ManyToOne
	private Kotxe kotxe;
	
	@ManyToOne
	private Driver driver;  
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Book> books=new Vector<Book>();
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Balorazio> balorazioak=new Vector<Balorazio>();
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Erreklamazio> erreklamazioak=new Vector<Erreklamazio>();
	
	public Ride(){
		super();
	}
	
	public Ride(Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k) {
		super();
		this.rideNumber = rideNumber;
		this.from = from;
		this.to = to;
		this.nPlaces = nPlaces;
		this.date=date;
		this.price=price;
		this.driver = driver;
		this.kotxe=k;
		this.eginda=false;
		LocalDateTime dataOrdua=LocalDateTime.now();
        DateTimeFormatter formatoa=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataOrduaFormateatuta=dataOrdua.format(formatoa);
        this.setWhenCreated(dataOrduaFormateatuta);
	}

	

	public Ride(String from, String to,  Date date, int nPlaces, float price, Driver driver,Kotxe k ) {
		super();
		this.from = from;
		this.to = to;
		this.nPlaces = nPlaces;
		this.date=date;
		this.price=price;
		this.driver = driver;
		this.kotxe=k;
		this.eginda=false;
		LocalDateTime dataOrdua=LocalDateTime.now();
        DateTimeFormatter formatoa=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataOrduaFormateatuta=dataOrdua.format(formatoa);
        this.setWhenCreated(dataOrduaFormateatuta);
	}
	
	/**
	 * Get the  number of the ride
	 * 
	 * @return the ride number
	 */
	public Integer getRideNumber() {
		return rideNumber;
	}

	
	/**
	 * Set the ride number to a ride
	 * 
	 * @param ride Number to be set	 */
	
	public void setRideNumber(Integer rideNumber) {
		this.rideNumber = rideNumber;
	}


	/**
	 * Get the origin  of the ride
	 * 
	 * @return the origin location
	 */

	public String getFrom() {
		return from;
	}


	/**
	 * Set the origin of the ride
	 * 
	 * @param origin to be set
	 */	
	
	public void setFrom(String origin) {
		this.from = origin;
	}

	/**
	 * Get the destination  of the ride
	 * 
	 * @return the destination location
	 */

	public String getTo() {
		return to;
	}


	/**
	 * Set the origin of the ride
	 * 
	 * @param destination to be set
	 */	
	public void setTo(String destination) {
		this.to = destination;
	}

	/**
	 * Get the free places of the ride
	 * 
	 * @return the available places
	 */
	
	/**
	 * Get the date  of the ride
	 * 
	 * @return the ride date 
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Set the date of the ride
	 * 
	 * @param date to be set
	 */	
	public void setDate(Date date) {
		this.date = date;
	}

	
	public float getnPlaces() {
		return nPlaces;
	}

	/**
	 * Set the free places of the ride
	 * 
	 * @param  f places to be set
	 */

	public void setnPlaces(int f) {
		this.nPlaces = f;
	}

	/**
	 * Get the driver associated to the ride
	 * 
	 * @return the associated driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * Set the driver associated to the ride
	 * 
	 * @param driver to associate to the ride
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}



	public String toString(){
		return rideNumber+";"+";"+from+";"+to+";"+date;  
	}
	public void addBook(Book book) {
		this.books.add(book);
	}
	public List<Book> getBookList(){
		return books;
	}
	public void printBookList() {
		for(Book book: this.books) {
			System.out.println(book.getEserlekuKop());
		}
	}
	public Kotxe getKotxea() {
		return this.kotxe;
	}

	public boolean isEginda() {
		return eginda;
	}

	public void setEginda(boolean eginda) {
		this.eginda = eginda;
	}
	public float calculatePrice(int seats) {
		return this.getPrice()*seats;
	}
	public int updatenPlaces(int seats) {
		this.setnPlaces((int)this.getnPlaces()-seats);
		return this.nPlaces;
	}

	public String getWhenCreated() {
		return whenCreated;
	}

	public void setWhenCreated(String whenCreated) {
		this.whenCreated = whenCreated;
	}
	public void addBalorazio(Balorazio balorazio) {
		this.balorazioak.add(balorazio);
	}
	public List<Balorazio> getBalorazioak(){
		return this.balorazioak;
	}
	public void addErreklamazio(Erreklamazio erreklamazio) {
		this.erreklamazioak.add(erreklamazio);
	}
	public List<Erreklamazio> getErreklamazioak(){
		return this.erreklamazioak;
	}
	public void removeBook(Book b) {
		this.books.remove(b);
	}
	public void removeReview(Balorazio b) {
		this.balorazioak.remove(b);
	}
	public void removeReport(Erreklamazio e) {
		this.erreklamazioak.remove(e);
	}
	public void refund() {
		for(Book b: books) {
				Traveler t=b.getTraveler();
				if(!this.isEginda()&&!this.allConfirmed()) {
					t.diruaSartu(b.getDiruIzoztuta());
				}
				t.removeBook(b);
			}	
	}
	
	public boolean allConfirmed() {
		for(Book b: books) {
			if(b.getEgoeraB()==false) {
				return false;
			}
		}
		return true;
	}
	
}
