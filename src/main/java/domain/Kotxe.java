package domain;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Kotxe implements Serializable{
	@XmlID
	@Id
	private String matrikula;
	private String marka;
	private String modeloa;
	private int eserlekuKopurua;
	@ManyToOne
	private Driver driver;  
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Ride> rides=new Vector <Ride>();
	public Kotxe() {
		super();
	}
	public Kotxe(String matrikula, String marka, String modeloa, int eserlekuKopurua, Driver driver) {
		this.matrikula=matrikula;
		this.setMarka(marka);
		this.setModeloa(modeloa);
		this.setEserlekuKopurua(eserlekuKopurua);
		this.driver=driver;
	}
	public String getMatrikula() {
		return this.matrikula;
	}
	public String getMarka() {
		return marka;
	}
	public void setMarka(String marka) {
		this.marka = marka;
	}
	public String getModeloa() {
		return modeloa;
	}
	public void setModeloa(String modeloa) {
		this.modeloa = modeloa;
	}
	public int getEserlekuKopurua() {
		return eserlekuKopurua;
	}
	public void setEserlekuKopurua(int eserlekuKopurua) {
		this.eserlekuKopurua = eserlekuKopurua;
	}
	public Driver getDriver() {
		return this.driver;
	}
	public void addRide(Ride r) {
		this.rides.add(r);
	}
	public void removeRide(Ride r) {
		this.rides.remove(r);
	}
}
