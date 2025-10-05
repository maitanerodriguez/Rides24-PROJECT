package domain;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Traveler extends User implements Serializable {
	private static final long serialVersionUID = 1L;
		@XmlIDREF
		@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
		private List<Book> books=new Vector<Book>();
		
		@XmlIDREF
		@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
		private List<Alerta> alertak=new Vector<Alerta>();
		
		@XmlIDREF
		@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
		private List<Balorazio> balorazioak=new Vector<Balorazio>();
		
		private String lastLogged;
		//private float dirua;
	
	public Traveler() {
		super();
	}
	public Traveler(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua) {
			super(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
	}
	/**
	public float getDirua() {
		return this.dirua;
	}
	public void setDirua(float f) {
		this.dirua=f;
	}
	*/
	public String getNan() {
		return this.NAN;
	}
	public void setNan(String newNan) {
		this.NAN=newNan;
	}
	
	public boolean existBook(Ride r) {
		for (Book b:books)
			if (b.getRide().getRideNumber().equals(r.getRideNumber()) && b.getTraveler().getNan().equals(this.getNan())) {
				return true;
			}
		return false;
	}
	/**
	public boolean hasCash(Ride ride, int seats) {
		float prezioa= ride.getPrice()*seats;
		if(prezioa>this.dirua) {
			return false;
		}else {
			return true;
		}
	}
	*/
	public String getLastLogged() {
		return this.lastLogged;
	}
	public void setLastLogged(String newLastLogged) {
		this.lastLogged=newLastLogged;
	}
	public Book addBook(Ride r, int seats) {
		Book book=new Book(this, r, seats);
		this.books.add(book);
		return book;
	}
	public List<Book> getBookListb(){
		return this.books;
	}
	public float diruaAtera(float kant) {
		this.setDirua(this.getDirua()-kant);
		LocalDateTime dataOrdua=LocalDateTime.now();
        DateTimeFormatter formatoa=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataOrduaFormateatuta=dataOrdua.format(formatoa);
        this.addMovement(dataOrduaFormateatuta, kant, ResourceBundle.getBundle("Etiquetas").getString("Mugimendua.diruaAteraSaldotik"));
        return this.getDirua();
	}
	public boolean doesAlertExist(String nondik, String nora, Date data)  {	
		for (Alerta a:alertak)
			if ( (java.util.Objects.equals(a.getNondik(),nondik)) && (java.util.Objects.equals(a.getNora(),nora)) && (java.util.Objects.equals(a.getData(),data)) )
			 return true;
		
		return false;
	}
	public Alerta addAlert(String nondik, String nora, Date data) {
		Alerta alerta=new Alerta(nondik, nora, data, this);
		this.alertak.add(alerta);
		return alerta;
	}
	public List<Alerta> getAlertak(){
		return this.alertak;
	}
	public boolean balorazioExist(Integer rideNumber) {
		for(Balorazio b: balorazioak) {
			Ride r=b.getRide();
			if(r.getRideNumber()==rideNumber) {
				return true;
			}
		}
		return false;
	}
	public Balorazio addBalorazio(Integer idBalorazio, int puntuazio, String komentario, String data, Ride ride) {
		Balorazio b=new Balorazio(idBalorazio, puntuazio, komentario, data, ride, this);
		this.balorazioak.add(b);
		return b;
	}
	public List<Balorazio> getBalorazioak(){
		return this.balorazioak;
	}
	public void removeBook(Book b) {
		this.getBookListb().remove(b);
	}
	public void removeBooks() {
		for(Book b: books){
			Ride r=b.getRide();
			if(b.getEgoeraB()==false){
				r.setnPlaces((int)r.getnPlaces()+b.getEserlekuKop());
			}
			r.removeBook(b);
			//this.books.remove(b);
			
		}
	}
	public void removeBalorazioak() {
		for(Balorazio b: balorazioak) {
			Ride r=b.getRide();
			r.removeReview(b);
			//this.balorazioak.remove(b);
		}
	}
	public void removeReview(Balorazio b) {
		this.balorazioak.remove(b);
	}
	/**
	public void removeAlerts() {
		for(Alerta a: alertak) {
			this.alertak.remove(a);
		}
	}
	*/

}	

