package domain;

import java.io.Serializable;

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
public class Balorazio implements Serializable{
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer idBalorazioa;
	private int puntuazioa;
	private String komentarioa;
	private String data;
	
	@ManyToOne
	private Ride ride;
	
	@ManyToOne
	private Traveler traveler;
	
	public Balorazio() {
		super();
	}
	
	public Balorazio(Integer idBalorazioa, int puntuazioa, String komentarioa, String data, Ride ride, Traveler traveler) {
		this.idBalorazioa=idBalorazioa;
		this.setPuntuazioa(puntuazioa);
		this.setKomentarioa(komentarioa);
		this.setData(data);
		this.ride=ride;
		this.traveler=traveler;
	}
	public Balorazio(int puntuazioa, String komentarioa, String data, Ride ride, Traveler traveler) {
		this.setPuntuazioa(puntuazioa);
		this.setKomentarioa(komentarioa);
		this.setData(data);
		this.ride=ride;
		this.traveler=traveler;
	}

	public int getPuntuazioa() {
		return puntuazioa;
	}

	public void setPuntuazioa(int puntuazioa) {
		this.puntuazioa = puntuazioa;
	}

	public String getKomentarioa() {
		return komentarioa;
	}

	public void setKomentarioa(String komentarioa) {
		this.komentarioa = komentarioa;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	public Ride getRide() {
		return this.ride;
	}
	public Traveler getTraveler() {
		return this.traveler;
	}

	public Integer getId() {
		return idBalorazioa;
	}
}
