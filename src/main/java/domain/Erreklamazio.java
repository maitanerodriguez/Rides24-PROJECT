package domain;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Erreklamazio implements Serializable{
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer idErreklamazioa;
	private String deskribapena;
	private String data;
	private String egoera;
	
	@ManyToOne
	private User erreklamatzailea;
	
	@ManyToOne
	private User erreklamatua;
	
	@ManyToOne
	private Ride ride;
	
	public Erreklamazio() {
		super();
	}
	public Erreklamazio(Integer idErreklamazio, String deskribapena, String data, User erreklamatua, Ride r, User erreklamatzailea) {
		this.idErreklamazioa=idErreklamazio;
		this.setDeskribapena(deskribapena);
		this.setData(data);
		this.egoera=ResourceBundle.getBundle("Etiquetas").getString("Erreklamazio.pending");
		this.erreklamatua=erreklamatua;
		this.ride=r;
		this.erreklamatzailea=erreklamatzailea;
	}
	
	public Erreklamazio(String deskribapena, String data, User erreklamatua, Ride r, User erreklamatzailea) {
		this.setDeskribapena(deskribapena);
		this.setData(data);
		this.egoera=ResourceBundle.getBundle("Etiquetas").getString("Erreklamazio.pending");
		this.erreklamatua=erreklamatua;
		this.ride=r;
		this.erreklamatzailea=erreklamatzailea;
	}
	public String getEgoera() {
		return egoera;
	}
	public void setEgoera(String egoera) {
		this.egoera = egoera;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDeskribapena() {
		return deskribapena;
	}
	public void setDeskribapena(String deskribapena) {
		this.deskribapena = deskribapena;
	}
	public Ride getRide() {
		return this.ride;
	}
	public User getErreklamatzailea() {
		return this.erreklamatzailea;
	}
	public User getErreklamatua() {
		return this.erreklamatua;
	}
	public Integer getIdErreklamazio() {
		return this.idErreklamazioa;
	}
	
}
