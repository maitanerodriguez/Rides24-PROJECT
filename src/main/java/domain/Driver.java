package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Driver extends User implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Ride> rides=new ArrayList<>();
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Kotxe> kotxeak=new ArrayList<>();
	
	public Driver() {
		super();
	}
	public Driver(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua) {
		super(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);

	}
	
	
	public String toString(){
		return super.toString()+rides;
	}
	
	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Ride addRide(String from, String to, Date date, int nPlaces, float price, Kotxe k)  {
        Ride ride=new Ride(from,to,date,nPlaces,price, this, k);
        this.rides.add(ride);
        return ride;
	}
	public Kotxe addCar(String marka, String modeloa, int eserlekuKopurua, String matrikula) {
		//String matrikula, String marka, String modeloa, int eserlekuKopurua, Driver driver
		Kotxe kotxe=new Kotxe(matrikula, marka, modeloa, eserlekuKopurua, this);
		this.kotxeak.add(kotxe);
		return kotxe;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String from, String to, Date date)  {	
		for (Ride r:rides)
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			 return true;
		
		return false;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Driver other = (Driver) obj;
		return this.NAN.equals(other.getNAN());
	}
	public Ride removeRide(String from, String to, Date date) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<=rides.size()) {
			r=rides.get(++index);
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) ) {
				found=true;
			}
			
		}
			 
		if (found) {
			rides.remove(index);
			return r;
		} else return null;
	}
	public List<Ride> getRideList(){
		return this.rides;
	}
	public List<Kotxe> getKotxeList(){
		return this.kotxeak;
	}
	public boolean existCar(String matrikula) {
		for (Kotxe ko: kotxeak)
			if (ko.getMatrikula().equals(matrikula)) {
				return true;
			}
		return false;
	}
	public void removeRide(Ride r) {
		this.getRideList().remove(r);
	}
	public void removeBidaiak() {
		for(Ride r: rides) {
			Kotxe k=r.getKotxea();
			k.removeRide(r);
			r.refund();
			
		}
	}
	
	public void removeBalorazioak() {
		for(Ride r: rides) {
			List<Balorazio> balorazioak=r.getBalorazioak();
			for(Balorazio b:balorazioak) {
				Traveler t=b.getTraveler();
				t.removeReview(b);
			}
		}
	}
	
}
