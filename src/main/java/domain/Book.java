package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Book implements Serializable {
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer bookNumber;
	private int eserlekuKop;
	private float diruIzoztuta;
	private String egoera;
	private boolean egoeraB;
	@ManyToOne
	private Traveler traveler; 
	@ManyToOne
	private Ride ride;
	public Book(){
		super();
	}
	public Book (Integer bookNumber, Traveler traveler, Ride ride, int eserlekuKop) {
		this.bookNumber=bookNumber;
		this.traveler=traveler;
		this.ride=ride;
		this.eserlekuKop=eserlekuKop;
		this.egoera=ResourceBundle.getBundle("Etiquetas").getString("Book.notCompleted");
		this.setEgoeraB(false);
	}
	public Book (Traveler traveler, Ride ride, int eserlekuKop) {
		this.traveler=traveler;
		this.ride=ride;
		this.eserlekuKop=eserlekuKop;
		this.egoera=ResourceBundle.getBundle("Etiquetas").getString("Book.notCompleted");
		this.setEgoeraB(false);
	}
	public int getEserlekuKop() {
		return this.eserlekuKop;
	}
	public void setEserlekuKop(int newEserlekuKop) {
		this.eserlekuKop=newEserlekuKop;
	}
	public Integer getBookNumber() {
		return this.bookNumber;
	}
	public Ride getRide() {
		return this.ride;
	}
	public Traveler getTraveler() {
		return traveler;
	}
	public void setRide(Ride ride) {
		this.ride=ride;
	}
	public float getDiruIzoztuta() {
		return diruIzoztuta;
	}
	public void setDiruIzoztuta(float diruIzoztuta) {
		this.diruIzoztuta = diruIzoztuta;
	}
	public String getEgoera() {
		return egoera;
	}
	public void setEgoera(String egoera) {
		this.egoera = egoera;
	}
	public boolean rideConfirmed() {
		return ride.isEginda();
	}
	public boolean isEgoeraB() {
		return egoeraB;
	}
	public void setEgoeraB(boolean egoeraB) {
		this.egoeraB = egoeraB;
	}
	public boolean getEgoeraB() {
		return this.egoeraB;
	}
}
