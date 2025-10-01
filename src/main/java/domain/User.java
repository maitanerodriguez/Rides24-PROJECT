package domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso ({Traveler.class, Driver.class, Admin.class})
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public abstract class User implements Serializable {
	
	private static final long serialVersionUID=1L;
	@XmlID
	@Id
	protected String NAN;
	private String log;
	private String password;
	private String email;
	private String izena;
	private String abizena;
	private String jaiotzeData;
	private int telefonoZenbakia;
	private String sexua;
	private float dirua;
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	public List<Erreklamazio> jasotakoErreklamazioak=new Vector<Erreklamazio>();
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	public List<Erreklamazio> bidalitakoErreklamazioak=new Vector<Erreklamazio>();
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	public List<Mugimendua> mugimenduak=new Vector<Mugimendua>();
	
	public User() {
		super();
	}
	public User(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua ) {
		this.log=log;
		this.password=password;
		this.NAN=NAN;
		this.email=email;
		this.izena=izena;
		this.setAbizena(abizena);
		this.setJaiotzeData(jaiotzeData);
		this.setTelefonoZenbakia(telefonoZenbakia);
		this.setSexua(sexua);
	}
	public String getLog() {
		return this.log;
	}
	public void setLog(String newLog) {
		this.log=newLog;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String newPassword) {
		this.password=newPassword;
	}
	public String getNAN() {
		return this.NAN;
	}
	public void setNAN(String newNAN) {
		this.log=newNAN;
	}
	public String toString() {
		return NAN+";"+izena;
	}
	public String getIzena() {
		return this.izena;
	}
	public void setIzena(String newIzena) {
		this.izena=newIzena;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String newEmail) {
		this.email=newEmail;
	}
	public String getJaiotzeData() {
		return jaiotzeData;
	}
	public void setJaiotzeData(String jaiotzeData) {
		this.jaiotzeData = jaiotzeData;
	}
	public String getAbizena() {
		return abizena;
	}
	public void setAbizena(String abizena) {
		this.abizena = abizena;
	}
	public String getSexua() {
		return sexua;
	}
	public void setSexua(String sexua) {
		this.sexua = sexua;
	}
	public int getTelefonoZenbakia() {
		return telefonoZenbakia;
	}
	public void setTelefonoZenbakia(int telefonoZenbakia) {
		this.telefonoZenbakia = telefonoZenbakia;
	}
	public float getDirua() {
		return dirua;
	}
	public void setDirua(float dirua) {
		this.dirua = dirua;
	}
	//String data, String deskribapena, User user, float diruKantitatea
	public Mugimendua addMovement(String data, float diruKantitatea, String deskribapena) {
		Mugimendua mugimendua=new Mugimendua (data, deskribapena, this, diruKantitatea);
		this.mugimenduak.add(mugimendua);
		return mugimendua;
	}
	public float diruaSartu(float kant) {
		this.setDirua(this.getDirua()+kant);
		LocalDateTime dataOrdua=LocalDateTime.now();
	    DateTimeFormatter formatoa=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	    String dataOrduaFormateatuta=dataOrdua.format(formatoa);
	    this.addMovement(dataOrduaFormateatuta, kant, ResourceBundle.getBundle("Etiquetas").getString("Mugimendua.diruaSartuSaldora"));
		return this.getDirua();
	}
	
	public boolean hasCash(Ride ride, int seats) {
		float prezioa= ride.getPrice()*seats;
		if(prezioa>this.dirua) {
			return false;
		}else {
			return true;
		}
	}
	public boolean erreklamazioExist(Integer rideNumber) {
		for(Erreklamazio e: bidalitakoErreklamazioak){
			Ride r=e.getRide();
			if(r.getRideNumber()==rideNumber) {
				return true;
			}
		}
		return false;
	}
	public Erreklamazio addSortutakoErreklamazio(String deskribapena, String data, User erreklamatua, Ride r ) {
		Erreklamazio e=new Erreklamazio (deskribapena, data, erreklamatua, r, this);
		this.bidalitakoErreklamazioak.add(e);
		return e;
	}
	public void addJasotakoErreklamazio(Erreklamazio e) {
		this.jasotakoErreklamazioak.add(e);
	}
	public List<Erreklamazio> getJasotakoErreklamazioak(){
		return this.jasotakoErreklamazioak;
	}
	public List<Erreklamazio> getSortutakoErreklamazioak(){
		return this.bidalitakoErreklamazioak;
	}
	public void removeBidalitakoErreklamazioak() {
		for (Erreklamazio e: bidalitakoErreklamazioak) {
			Ride r=e.getRide();
			r.removeReport(e);
			User er=e.getErreklamatua();
			er.jasotakoErreklamazioak.remove(e);
			//er.removeErreklamazioaJaso(e);
			//this.bidalitakoErreklamazioak.remove(e);
		}
	}
	
	public void removeJasotakoErreklamazioak() {
		for(Erreklamazio e: jasotakoErreklamazioak) {
			Ride r=e.getRide();
			r.removeReport(e);
			User er=e.getErreklamatzailea();
			er.bidalitakoErreklamazioak.remove(e);
		}
	}
	
	/**
	public void removeMugimenduak() {
		for(Mugimendua m: mugimenduak) {
			this.mugimenduak.remove(m);
		}
	}
	*/
	/**
	public void removeErreklamazioaJaso(Erreklamazio e) {
		this.jasotakoErreklamazioak.remove(e);
	}
	public void removeErreklamazioaBidali(Erreklamazio e) {
		this.bidalitakoErreklamazioak.remove(e);
	}
	*/
	
}
